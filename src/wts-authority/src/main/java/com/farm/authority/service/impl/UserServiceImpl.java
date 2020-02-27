package com.farm.authority.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.dao.OrganizationDaoInter;
import com.farm.authority.dao.PostDaoInter;
import com.farm.authority.dao.UserDaoInter;
import com.farm.authority.dao.UserorgDaoInter;
import com.farm.authority.dao.UserpostDaoInter;
import com.farm.authority.domain.Action;
import com.farm.authority.domain.AuthMenuImpl;
import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.authority.domain.User;
import com.farm.authority.domain.Userorg;
import com.farm.authority.domain.Userpost;
import com.farm.authority.exception.LicenceException;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.domain.WebMenu;
import com.farm.core.auth.util.AuthenticateInter;
import com.farm.core.auth.util.AuthenticateProvider;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.time.TimeTool;
import com.farm.exread.ExcelReader;
import com.farm.exread.ReaderHandle;
import com.farm.exread.service.ExcelReaderImpl;
import com.farm.exread.service.ReaderConfig;
import com.farm.exread.service.ReaderConfig.ColumnType;
import com.farm.parameter.FarmParameterService;
import com.farm.util.validate.ValidUtils;

/* *
 *功能：用户服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：王东
 *日期：20141119144919
 *说明：
 */
@Service
public class UserServiceImpl implements UserServiceInter {
	@Resource
	private UserDaoInter userDaoImpl;
	@Resource
	private UserpostDaoInter userpostDaoImpl;
	@Resource
	private PostDaoInter postDaoImpl;
	@Resource
	private OrganizationDaoInter organizationDao;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;
	@Resource
	private UserorgDaoInter userorgDaoImpl;
	private AuthenticateInter authUtil = AuthenticateProvider.getInstance();
	private final static Logger log = Logger.getLogger(UserServiceImpl.class);

	// private static final Logger log =
	// Logger.getLogger(UserServiceImpl.class);
	private void checkUserLimit() {
		int num = userDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("STATE", "2", "!=")).toList());
		if (num > FarmAuthorityService.getPLOGS_USERNUM()) {
			//默认5000人
			throw new LicenceException("用户数量超出授权限制" + FarmAuthorityService.getPLOGS_USERNUM() + "人,已删除用户不计入限制人数!");
		}
	}

	@Override
	@Transactional
	public User insertUserEntity(User entity, LoginUser user, String orgId, String postIds) {
		checkUserLimit();
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setMuser(user.getId());
		entity.setUtime(TimeTool.getTimeDate14());
		if (validateIsRepeatLoginName(entity.getLoginname(), null)) {
			throw new RuntimeException("登录名已经存在!" + entity.getName() + "[" + entity.getLoginname() + "]");
		}
		entity.setPassword(authUtil.encodeLoginPasswordOnMd5(
				FarmParameterService.getInstance().getParameter("config.default.password"), entity.getLoginname()));
		entity = userDaoImpl.insertEntity(entity);
		// 保存用户机构关系
		if (StringUtils.isNotBlank(orgId)) {
			userorgDaoImpl.insertEntity(new Userorg("", orgId, entity.getId()));
		}
		// 保存用户岗位关系
		if (StringUtils.isNotBlank(postIds)) {
			String[] postIdArr = postIds.split(",");
			for (String postId : postIdArr) {
				userpostDaoImpl.insertEntity(new Userpost("", postId, entity.getId()));
			}
		}
		return entity;
	}

	@Override
	@Transactional
	public User insertUserEntity(User entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setMuser(user.getId());
		entity.setUtime(TimeTool.getTimeDate14());
		if (validateIsRepeatLoginName(entity.getLoginname(), null)) {
			throw new RuntimeException("登录名已经存在!");
		}
		if (entity.getPassword() == null || entity.getPassword().isEmpty()) {
			entity.setPassword(FarmParameterService.getInstance().getParameter("config.default.password"));
		}
		entity.setPassword(authUtil.encodeLoginPasswordOnMd5(entity.getPassword(), entity.getLoginname()));

		entity = userDaoImpl.insertEntity(entity);
		return entity;
	}

	@Override
	@Transactional
	public User insertUserEntity(String name, String loginname, String password, String imgid) {
		User entity = new User();
		entity.setLoginname(loginname);
		entity.setName(name);
		entity.setPassword(password);
		entity.setType("1");
		entity.setImgid(imgid);
		entity.setState("1");
		return insertUserEntity(entity, new LoginUser() {
			@Override
			public String getName() {
				return "system";
			}

			@Override
			public String getLoginname() {
				return "NONE";
			}

			@Override
			public String getId() {
				return "system";
			}

			@Override
			public String getType() {
				return "NONE";
			}

			@Override
			public String getIp() {
				return "NONE";
			}
		});
	}

	@Override
	@Transactional
	public User insertUserEntity(String name, String loginname, String password) {
		User entity = new User();
		entity.setLoginname(loginname);
		entity.setName(name);
		entity.setPassword(password);
		entity.setType("1");
		entity.setState("1");
		return insertUserEntity(entity, new LoginUser() {
			@Override
			public String getName() {
				return "system";
			}

			@Override
			public String getLoginname() {
				return "NONE";
			}

			@Override
			public String getId() {
				return "system";
			}

			@Override
			public String getType() {
				return "NONE";
			}

			@Override
			public String getIp() {
				return "NONE";
			}
		});
	}

	@Override
	@Transactional
	public User editUserEntity(User entity, LoginUser user) {
		User entity2 = userDaoImpl.getEntity(entity.getId());
		if (validateIsRepeatLoginName(entity.getLoginname(), entity2.getId())) {
			throw new RuntimeException("登录名已经存在!");
		}
		if (entity2.getState().equals("2")) {
			throw new RuntimeException("该用户已被删除，无法修改");
		}
		entity2.setMuser(user.getId());
		entity2.setUtime(TimeTool.getTimeDate14());
		if (!ValidUtils.isEmptyString(entity.getLoginname())) {
			entity2.setLoginname(entity.getLoginname());
		}
		if (!ValidUtils.isEmptyString(entity.getState())) {
			entity2.setState(entity.getState());
		}
		if (!ValidUtils.isEmptyString(entity.getType())) {
			entity2.setType(entity.getType());
		}
		if (!ValidUtils.isEmptyString(entity.getImgid())) {
			entity2.setImgid(entity.getImgid());
		}
		if (!ValidUtils.isEmptyString(entity.getComments())) {
			entity2.setComments(entity.getComments());
		}
		if (!ValidUtils.isEmptyString(entity.getName())) {
			entity2.setName(entity.getName());
		}
		userDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public User editUserEntity(User entity, LoginUser user, String orgId, String postIds) {
		User entity2 = editUserEntity(entity, user);
		if (StringUtils.isNotBlank(orgId)) {
			// 更新用户机构关系
			setUserOrganization(entity.getId(), orgId, user);
		}
		if (StringUtils.isNotBlank(postIds)) {
			// 更新用户岗位
			setUserPost(entity.getId(), postIds, user);
		}
		return entity2;
	}

	@Override
	@Transactional
	public void setUserPost(String userid, String postids, LoginUser currentUser) {
		// 更新用户岗位关系
		userpostDaoImpl.deleteEntitys(new DBRule("USERID", userid, "=").getDBRules());
		String[] postIdArr = postids.split(",");
		for (String postId : postIdArr) {
			userpostDaoImpl.insertEntity(new Userpost("", postId, userid));
		}
	}

	@Override
	@Transactional
	public boolean validateIsRepeatLoginName(String loginname, String userId) {
		List<User> list = null;
		if (userId == null || userId.trim().equals("")) {
			list = userDaoImpl.findUserByLoginName(loginname.trim());
		} else {
			list = userDaoImpl.findUserByLoginName(loginname.trim(), userId);
		}
		return list.size() > 0;
	}

	@Override
	@Transactional
	public void deleteUserEntity(String id, LoginUser user) {
		String[] idArr = id.split(",");
		for (int i = 0; i < idArr.length; i++) {
			User entity2 = userDaoImpl.getEntity(idArr[i]);
			entity2.setMuser(user.getId());
			entity2.setUtime(TimeTool.getTimeDate14());
			entity2.setState("2");
			entity2.setLoginname(entity2.getId());
			userDaoImpl.editEntity(entity2);
		}
	}

	@Override
	@Transactional
	public User getUserEntity(String id) {
		if (id == null) {
			return null;
		}
		return userDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createUserSimpleQuery(DataQuery query, LoginUser currentUser) {
		DataQuery dbQuery = DataQuery
				.init(query,
						"( SELECT A.ID AS ID, A.LOGINTIME AS LOGINTIME, A.LOGINNAME AS LOGINNAME, A.STATE AS STATE, A.TYPE AS TYPE, A.COMMENTS AS COMMENTS, A. NAME AS NAME, A3. NAME AS ORGNAME,A3.TREECODE AS TREECODE, A.CTIME AS CTIME, ( SELECT count(*) FROM ALONE_AUTH_OUTUSER WHERE USERID = A.ID ) AS OUTUSER FROM ALONE_AUTH_USER A LEFT JOIN ALONE_AUTH_USERORG B ON A.ID = B.USERID LEFT JOIN ALONE_AUTH_ORGANIZATION A3 ON B.ORGANIZATIONID = A3.ID ) f LEFT JOIN ALONE_AUTH_USERPOST C ON C.USERID = f.ID LEFT JOIN ALONE_AUTH_POST D ON D.ID = C.POSTID",
						"f.ID as ID,LOGINTIME,LOGINNAME,STATE,TYPE,COMMENTS,f.NAME as NAME,ORGNAME,f.CTIME as CTIME,OUTUSER,TREECODE")
				.setDistinct(true);
		User entity = userDaoImpl.getEntity(currentUser.getId());
		if (entity.getType() != null && (!entity.getType().equals("3"))) {
			dbQuery.addRule(new DBRule("TYPE", 3, "!="));
		}
		return dbQuery;
	}

	// ----------------------------------------------------------------------------------

	@Override
	@Transactional
	public void initDefaultPassWord(String userid, LoginUser currentUser) {
		User entity2 = userDaoImpl.getEntity(userid);
		entity2.setMuser(currentUser.getId());
		entity2.setUtime(TimeTool.getTimeDate14());
		String defaultPassword = FarmParameterService.getInstance().getParameter("config.default.password");
		String userPassword = authUtil.encodeLoginPasswordOnMd5(defaultPassword, entity2.getLoginname());
		entity2.setPassword(userPassword);
		log.info("用户密码初始化,默认密码" + defaultPassword + ",当前用户" + entity2.getLoginname() + "密码为" + userPassword);
		userDaoImpl.editEntity(entity2);
	}

	@Override
	@Transactional
	public User getUserByLoginName(String loginName) {
		List<User> users = userDaoImpl.findUserByLoginName(loginName);
		if (users.size() <= 0) {
			return null;
		}
		if (users.size() > 1) {
			throw new RuntimeException("该登录名返回了多个用户！");
		}
		return users.get(0);
	}

	@Override
	@Transactional
	public String setLoginTime(String userId) {
		User entity2 = userDaoImpl.getEntity(userId);
		String lastLoginTime = entity2.getLogintime();
		entity2.setLogintime(TimeTool.getTimeDate14());
		userDaoImpl.editEntity(entity2);
		return lastLoginTime;
	}

	@Override
	@Transactional
	public DataQuery createUserPostQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"ALONE_AUTH_ORGANIZATION ORG "
						+ "INNER JOIN ALONE_AUTH_USERORG USERORG ON ORG.ID = USERORG.ORGANIZATIONID "
						+ "INNER JOIN ALONE_AUTH_USER USER ON USERORG.USERID = USER.ID",
				"USER.ID AS USERID,USER.LOGINTIME AS LOGINTIME,USER.NAME AS USERNAME,USER.STATE AS USERSTATE");
		dbQuery.addRule(new DBRule("USER.STATE", "1", "="));
		return dbQuery;
	}

	@Override
	@Transactional
	public List<Action> getUserActions(String userId) {
		DataQuery dbQuery = DataQuery.getInstance(1, "d.id,d.AUTHKEY,d.NAME,d.COMMENTS,d.STATE,d.CHECKIS,d.LOGINIS",
				"alone_auth_userpost a LEFT JOIN alone_auth_postaction b ON a.POSTID =b.POSTID LEFT JOIN alone_auth_actiontree c ON b.MENUID=c.ID LEFT JOIN alone_auth_action d ON d.ID=c.ACTIONID");
		dbQuery.addRule(new DBRule("d.STATE", "1", "="));
		dbQuery.addRule(new DBRule("a.USERID", userId, "="));
		dbQuery.setDistinct(true);
		dbQuery.setPagesize(5000);
		List<Action> list = new ArrayList<Action>();
		try {
			for (Map<String, Object> node : dbQuery.search().getResultList()) {
				Action action = new Action();
				action.setAuthkey(node.get("D_AUTHKEY") != null ? node.get("D_AUTHKEY").toString() : null);
				action.setId(node.get("D_ID") != null ? node.get("D_ID").toString() : null);
				action.setName(node.get("D_NAME") != null ? node.get("D_NAME").toString() : null);
				action.setComments(node.get("D_COMMENTS") != null ? node.get("D_COMMENTS").toString() : null);
				action.setState(node.get("D_STATE") != null ? node.get("D_STATE").toString() : null);
				action.setCheckis(node.get("D_CHECKIS") != null ? node.get("D_CHECKIS").toString() : null);
				action.setLoginis(node.get("D_LOGINIS") != null ? node.get("D_LOGINIS").toString() : null);
				list.add(action);
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return list;
	}

	@Override
	@Transactional
	public List<WebMenu> getUserMenus(String userId) {
		DataQuery query = DataQuery.getInstance(1, "SORT,ID,PARENTID,NAME,TYPE,STATE,ICON,IMGID,PARAMS,AUTHKEY",
				"(SELECT c.SORT,c.ID,c.PARENTID,c.NAME,c.TYPE,c.STATE,c.ICON,c.IMGID,c.PARAMS,d.AUTHKEY FROM  alone_auth_userpost a LEFT JOIN alone_auth_postaction b ON a.POSTID =b.POSTID LEFT JOIN alone_auth_actiontree c ON b.MENUID=c.ID LEFT JOIN alone_auth_action d ON d.ID=c.ACTIONID WHERE (d.STATE = '1'||d.STATE IS NULL) and c.STATE='1' and a.userid='"
						+ userId + "' and c.type!='3' order by LENGTH(c.TREECODE),c.SORT asc) e");
		List<WebMenu> menus = new ArrayList<WebMenu>();
		query.setPagesize(1000);
		query.addSort(new DBSort("SORT", "asc"));
		query.setNoCount();
		query.setDistinct(true);
		try {
			for (Map<String, Object> map : query.search().getResultList()) {
				AuthMenuImpl node = new AuthMenuImpl();
				node.setIcon(map.get("ICON") != null ? map.get("ICON").toString() : null);
				node.setId(map.get("ID") != null ? map.get("ID").toString() : null);
				node.setName(map.get("NAME") != null ? map.get("NAME").toString() : null);
				node.setParams(map.get("PARAMS") != null ? map.get("PARAMS").toString() : null);
				node.setParentid(map.get("PARENTID") != null ? map.get("PARENTID").toString() : null);
				node.setUrl(map.get("AUTHKEY") != null ? map.get("AUTHKEY").toString() : null);
				menus.add(node);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return menus;
	}

	@Override
	@Transactional
	public List<Post> getUserPosts(String userId) {
		List<Userpost> userposts = userpostDaoImpl.selectEntitys(new DBRule("USERID", userId, "=").getDBRules());
		List<Post> list = new ArrayList<Post>();
		for (Userpost userPost : userposts) {
			Post post = postDaoImpl.getEntity(userPost.getPostid());
			if (post != null) {
				list.add(post);
			}
		}
		return list;
	}

	@Override
	@Transactional
	public boolean editUserPasswordByLoginName(String loginname, String oldPassword, String newPassword) {
		User user = getUserByLoginName(loginname);
		if (user == null) {
			throw new RuntimeException("不存在该用户!");
		}
		if (authUtil.encodeLoginPasswordOnMd5(oldPassword, loginname).equals(user.getPassword())) {
			// 验证成功,修改密码
			user.setPassword(authUtil.encodeLoginPasswordOnMd5(newPassword, loginname));
			userDaoImpl.editEntity(user);
			return true;
		} else {
			throw new RuntimeException("原密码错误!");
		}
	}

	@Override
	@Transactional
	public List<String> getUserPostIds(String userId) {
		List<Userpost> userposts = userpostDaoImpl.selectEntitys(new DBRule("USERID", userId, "=").getDBRules());
		List<String> list = new ArrayList<String>();
		for (Userpost userPost : userposts) {
			list.add(userPost.getPostid());
		}
		return list;
	}

	@Override
	@Transactional
	public Organization getUserOrganization(String userId) {
		return getOrg(userId);
	}

	@Override
	@Transactional
	public User registUser(final User user) {
		LoginUser noneuser = new LoginUser() {
			@Override
			public String getName() {
				return user.getName();
			}

			@Override
			public String getLoginname() {
				return user.getLoginname();
			}

			@Override
			public String getId() {
				return user.getLoginname();
			}

			@Override
			public String getType() {
				return user.getType();
			}

			@Override
			public String getIp() {
				return "NONE";
			}
		};
		return insertUserEntity(user, noneuser);
	}

	@Override
	@Transactional
	public User registUser(final User user, String orgid) {
		// 保存新增用户
		LoginUser noneuser = new LoginUser() {
			@Override
			public String getName() {
				return user.getName();
			}

			@Override
			public String getLoginname() {
				return user.getLoginname();
			}

			@Override
			public String getId() {
				return user.getLoginname();
			}

			@Override
			public String getType() {
				return user.getType();
			}

			@Override
			public String getIp() {
				return "NONE";
			}
		};
		String defaultOrg = orgid;
		User user2 = insertUserEntity(user, noneuser, defaultOrg, null);
		return user2;
	}

	@Override
	public Organization getOrg(String id) {
		try {
			DataQuery query = DataQuery.getInstance(1,
					"ORG.ID as ID,ORG.TYPE as TYPE,ORG.SORT as SORT,ORG.PARENTID as PARENTID,ORG.MUSER as MUSER,"
							+ "ORG.CUSER as CUSER,ORG.STATE as STATE,ORG.UTIME as UTIME,ORG.CTIME as CTIME,"
							+ "ORG.COMMENTS as COMMENTS,ORG.NAME as NAME,ORG.TREECODE as TREECODE",
					"ALONE_AUTH_USERORG USERORG "
							+ "INNER JOIN ALONE_AUTH_ORGANIZATION ORG ON USERORG.ORGANIZATIONID = ORG.ID");
			query.addRule(new DBRule("USERORG.USERID", id, "="));
			query.setPagesize(1000);
			query.setNoCount();
			DataResult result = query.search();
			List<Object> orgList = result.getObjectList(Organization.class);
			if (orgList.size() > 0) {
				return (Organization) orgList.get(0);
			}
			return null;
		} catch (SQLException e) {
			log.error(e + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<Post> getPost(String id) {
		// ID,EXTENDIS,NAME,ORGANIZATIONID,PSTATE,EUSER,EUSERNAME,CUSER,CUSERNAME,ETIME,CTIME
		try {
			DataQuery query = DataQuery.getInstance(1,
					"POST.ID AS ID,POST.EXTENDIS AS EXTENDIS,POST.NAME AS NAME,POST.ORGANIZATIONID AS ORGANIZATIONID,POST.PSTATE AS PSTATE,POST.EUSER AS EUSER,POST.EUSERNAME AS EUSERNAME,POST.CUSER AS CUSER,POST.CUSERNAME AS CUSERNAME,POST.ETIME AS ETIME,POST.CTIME AS CTIME",
					"ALONE_AUTH_USERPOST USERPOST " + "LEFT JOIN ALONE_AUTH_POST POST ON USERPOST.POSTID = POST.ID");
			query.addRule(new DBRule("USERPOST.USERID", id, "="));
			query.addSqlRule("and POST.ID is not null");
			DataResult result = query.search();
			result.getObjectList(Post.class);
			return result.getObjectList(Post.class);
		} catch (SQLException e) {
			log.error(e + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public DataQuery createOrgUserQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"ALONE_AUTH_ORGANIZATION ORG "
						+ "INNER JOIN ALONE_AUTH_USERORG USERORG ON ORG.ID = USERORG.ORGANIZATIONID "
						+ "INNER JOIN ALONE_AUTH_USER USER ON USERORG.USERID = USER.ID",
				"USER.ID AS USERID,USER.LOGINTIME AS LOGINTIME,USER.NAME AS USERNAME,USER.STATE AS USERSTATE");
		dbQuery.addRule(new DBRule("USER.STATE", "1", "="));
		return dbQuery;
	}

	@Override
	@Transactional
	public void editCurrentUser(String id, String name, String photoid, String orgid) {
		// 更新用户
		User user = userDaoImpl.getEntity(id);
		if (StringUtils.isNotBlank(name)) {
			user.setName(name);
		}
		user.setImgid(photoid);
		userDaoImpl.editEntity(user);
		// 更新机构
		if (StringUtils.isNotBlank(orgid)) {
			// 更新用户机构关系
			userorgDaoImpl.deleteEntitys(new DBRule("USERID", id, "=").getDBRules());
			userorgDaoImpl.insertEntity(new Userorg("", orgid, id));
			// 删除岗位（岗位暂时无用，wd说的）
			userpostDaoImpl.deleteEntitys(new DBRule("userid", id, "=").getDBRules());
		}
	}

	@Override
	@Transactional
	public void editUserPassword(String id, String password, String newPassword) {
		User user = userDaoImpl.getEntity(id);
		String oldPwd = authUtil.encodeLoginPasswordOnMd5(password, user.getLoginname());
		if (!user.getPassword().equals(oldPwd)) {
			throw new RuntimeException("旧密码错误!");
		}

		String newPwd = authUtil.encodeLoginPasswordOnMd5(newPassword, user.getLoginname());
		user.setPassword(newPwd);
		userDaoImpl.editEntity(user);
	}

	@Override
	@Transactional
	public void editUserPassword(String userid, String newPassword) {
		User user = userDaoImpl.getEntity(userid);
		String newPwd = authUtil.encodeLoginPasswordOnMd5(newPassword, user.getLoginname());
		user.setPassword(newPwd);
		userDaoImpl.editEntity(user);
	}

	@Override
	@Transactional
	public boolean validCurrentUserPwd(String userid, String password) {
		User user = userDaoImpl.getEntity(userid);
		String pwdForMd5 = authUtil.encodeLoginPasswordOnMd5(password, user.getLoginname());
		if (!user.getPassword().equals(pwdForMd5)) {
			return false;
		}
		return true;
	}

	@Override
	public Integer getUsersNum() {
		return userDaoImpl.getUsersNum();
	}

	@Override
	@Transactional
	public void doUserImport(MultipartFile file, final LoginUser currentUser) {
		try {
			// 所有需要导入的用户
			final List<Map<String, String>> users = new ArrayList<>();
			// 校验数据有效性
			CommonsMultipartFile cmfile = (CommonsMultipartFile) file;
			if (!"application/vnd.ms-excel".equals(cmfile.getContentType())) {
				throw new RuntimeException("模板格式不正确。请从\"用户管理/人员导入/模板下载\"下载。");
			}

			if (cmfile.getSize() >= 10000000) {
				throw new RuntimeException("文件不能大于10M");
			}

			final Map<Organization, List<Map<String, Object>>> orgPostMap = new HashMap<Organization, List<Map<String, Object>>>();
			List<Organization> orgList = organizationServiceImpl.getList();
			for (Organization org : orgList) {
				List<Map<String, Object>> postList = organizationServiceImpl.getPostListWithPOrgPost(org.getId());
				orgPostMap.put(org, postList);
			}

			ExcelReader reader = getExcelReader(cmfile);
			reader.read(new ReaderHandle() {
				int index = -1;

				@Override
				public void handle(Map<String, Object> node) {
					String userName = node.get("USERNAME") == null ? "" : node.get("USERNAME").toString().trim();
					String loginName = node.get("LOGINNAME") == null ? "" : node.get("LOGINNAME").toString().trim();
					String orgName = node.get("ORGNAME") == null ? "" : node.get("ORGNAME").toString().trim();
					String postName = node.get("POSTNAME") == null ? "" : node.get("POSTNAME").toString().trim();
					String content = node.get("PCONTENT") == null ? "" : node.get("PCONTENT").toString().trim();// 军衔
					String location = "错误位置:第" + index + "行，问题人员'" + userName + "'";
					index++;
					if (index == 0) {
						if (!("姓名".equals(userName) || !("登录名").equals(loginName) || !("组织机构").equals(orgName)
								|| !("所属岗位").equals(postName) || !("备注").equals(content))) {
							throw new RuntimeException("模板格式不正确。请从\"用户管理/人员导入/模板下载\"下载。");
						}
						return;// 第一行不需要处理
					}
					if (index == 1) {
						return;// 第二行不需要处理
					}

					if (userName.length() < 2 || userName.length() > 16) {
						throw new RuntimeException("姓名（" + userName + "）必须大于2个字符或小于16个字符！" + location);
					}
					if (loginName.length() < 4 || loginName.length() > 16) {// 登录名称重复，在添加时有验证
						throw new RuntimeException("登录名称（" + loginName + "）必须在4-16个字符之间！" + location);
					}
					if (!content.isEmpty() && content.length() > 64) {
						throw new RuntimeException("备注（" + content + "）最长64位！" + location);
					}

					boolean existOrgPost = false;
					String orgId = null, postId = null;
					for (Entry<Organization, List<Map<String, Object>>> entry : orgPostMap.entrySet()) {
						if (existOrgPost) {
							break;
						}
						Organization org = entry.getKey();
						if (org.getName().equals(orgName) || org.getId().equals(orgName)) {
							// 检查组织机构是否存在
							List<Map<String, Object>> postList = entry.getValue();
							existOrgPost = true;
							orgId = org.getId();
							if (StringUtils.isNotBlank(postName)) {
								// 检查岗位是否存在
								for (Map<String, Object> map : postList) {
									if (map.get("POSTNAME").equals(postName)) {
										postId = map.get("POSTID").toString();
										break;
									}
									throw new RuntimeException("所填写岗位不属于该组织机构！" + location);
								}
							}
						}
					}
					if (!existOrgPost) {
						throw new RuntimeException("不存在组织机构！" + location);
					}
					Map<String, String> usernode = new HashMap<>();
					usernode.put("NAME", userName);
					usernode.put("LOGINNAME", loginName);
					usernode.put("TYPE", "1");
					usernode.put("STATE", "1");
					usernode.put("COMMENTS", content);
					usernode.put("ORGID", orgId);
					usernode.put("POSTID", postId);
					users.add(usernode);
				}
			});

			for (Map<String, String> usernode : users) {
				List<User> haveusers = userDaoImpl.findUserByLoginName(usernode.get("LOGINNAME"));
				if (haveusers.size() > 0) {
					// 修改
					User havuser = haveusers.get(0);
					editUserEntity(havuser, currentUser, usernode.get("ORGID"), usernode.get("POSTID"));
				} else {
					// 插入
					User user = new User();
					user.setName(usernode.get("NAME"));
					user.setLoginname(usernode.get("LOGINNAME"));
					user.setType(usernode.get("TYPE"));
					user.setState(usernode.get("STATE"));
					user.setComments(usernode.get("COMMENTS"));
					insertUserEntity(user, currentUser, usernode.get("ORGID"), usernode.get("POSTID"));
				}
			}
		} catch (Exception e) {
			log.error(e + e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	private ExcelReader getExcelReader(CommonsMultipartFile cmfile) throws IOException {
		ReaderConfig config = ReaderConfig.newInstance(0, 0, 0);
		config.addColumn(0, "USERNAME", ColumnType.STRING);
		config.addColumn(1, "LOGINNAME", ColumnType.STRING);
		config.addColumn(2, "ORGNAME", ColumnType.STRING);
		config.addColumn(3, "POSTNAME", ColumnType.STRING);
		config.addColumn(4, "PCONTENT", ColumnType.STRING);
		ExcelReader reader = ExcelReaderImpl.getInstance(config, cmfile.getInputStream());
		return reader;
	}

	@Override
	@Transactional
	public DataResult searchUserByUsernameAndOrgname(String word, Integer pagenum) {
		DataQuery query = DataQuery.getInstance(pagenum,
				"A.ID AS ID ,A.NAME AS NAME,C.NAME AS ORGNAME,A.IMGID AS IMGID",
				"alone_auth_user AS a LEFT JOIN alone_auth_userorg AS b ON a.ID = b.USERID LEFT JOIN alone_auth_organization AS c ON b.ORGANIZATIONID = c.ID");
		query.addRule(new DBRule("a.STATE", "1", "="));
		if (word != null && !word.isEmpty()) {
			DataQuerys.wipeVirus(word);
			query.addSqlRule(" and (a.NAME like '%" + word + "%' or c.NAME like '%" + word + "%')");
		}
		try {
			return query.search();
		} catch (SQLException e) {
			log.error(e + e.getMessage(), e);
			return DataResult.getInstance();
		}
	}

	@Override
	@Transactional
	public void setUserOrganization(String userid, String orgid, LoginUser currentUser) {
		if (orgid == null || orgid.isEmpty() || userid == null || userid.isEmpty()) {
			log.warn("入参为空，该操作无效！(String userid, String orgid)");
			return;
		}
		// 更新用户机构关系
		userorgDaoImpl.deleteEntitys(new DBRule("USERID", userid, "=").getDBRules());
		userorgDaoImpl.insertEntity(new Userorg("", orgid, userid));
		String currentUserId = "NONE";
		if (currentUser != null) {
			currentUserId = currentUser.getId();
		}
	}

	@Override
	@Transactional
	public List<User> getSuperUsers() {
		List<DBRule> rules = new ArrayList<>();
		rules.add(new DBRule("type", "3", "="));
		List<User> list = userDaoImpl.selectEntitys(rules);
		return list;
	}

	@Override
	@Transactional
	public User editUserState(String userid, String state, LoginUser currentUser) {
		User entity = userDaoImpl.getEntity(userid);
		entity.setState(state);
		userDaoImpl.editEntity(entity);
		return entity;
	}

	@Override
	@Transactional
	public List<String> getSuperUserids() {
		List<DBRule> rules = new ArrayList<>();
		rules.add(new DBRule("type", "3", "="));
		List<User> list = userDaoImpl.selectEntitys(rules);
		List<String> ids = new ArrayList<>();
		for (User user : list) {
			ids.add(user.getId());
		}
		return ids;
	}

	@Override
	@Transactional
	public void editUserType(String userid, String type, LoginUser user) {
		User entity = userDaoImpl.getEntity(userid);
		entity.setType(type);
		userDaoImpl.editEntity(entity);
	}

	@Override
	@Transactional
	public void visitUserHomePage(String userid, LoginUser currentUser, String currentIp) {
		// @auto
	}

	@Override
	@Transactional
	public boolean syncRemoteUser(User remoteUser) {
		User localUser = userDaoImpl.getEntity(remoteUser.getId());
		if (localUser == null) {
			List<User> users = userDaoImpl.findUserByLoginName(remoteUser.getLoginname());
			for (User node : users) {
				deleteUserEntity(node.getId(), node);
			}
			// 新增
			userDaoImpl.insertSqlEntity(remoteUser);
			return true;
		} else {
			// 修改
			try {
				BeanUtils.copyProperties(localUser, remoteUser);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			userDaoImpl.editEntity(localUser);
			return false;
		}
	}

}
