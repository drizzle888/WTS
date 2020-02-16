package com.farm.web.init;

import java.net.MalformedURLException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.farm.parameter.FarmParameterService;
import com.farm.web.task.ServletInitJobInter;

public class InitRmiInter implements ServletInitJobInter {
	private static final Logger log = Logger.getLogger(InitRmiInter.class);

	@Override
	public void execute(ServletContext context) {
//		String uri = null;
//		try {
//			if (FarmParameterService.getInstance().getParameter("config.local.rmi.state").toUpperCase()
//					.equals("FALSE")) {
//				return;
//			}
//			int port = Integer.valueOf(FarmParameterService.getInstance().getParameter("config.local.rmi.port"));
//			uri = "rmi://127.0.0.1:" + port + "/wcpapp";
//			wcp= new WcpAppImpl();
//			LocateRegistry.createRegistry(port);
//			Naming.rebind(uri, wcp);
//			log.info("启动RMI服务" + uri);
//		} catch (RemoteException e) {
//			System.out.println("创建远程对象发生异常！" + uri);
//			log.error(e + e.getMessage(), e);
//		} catch (AlreadyBoundException e) {
//			System.out.println("发生重复绑定对象异常！" + uri);
//			log.error(e + e.getMessage(), e);
//		} catch (MalformedURLException e) {
//			System.out.println("发生URL畸形异常！" + uri);
//			log.error(e + e.getMessage(), e);
//		}
	}
}
