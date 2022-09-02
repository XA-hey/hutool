package cn.hutool.extra.ftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.extra.ssh.Sftp;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class FtpTest {

	@Test
	@Ignore
	public void cdTest() {
		Ftp ftp = new Ftp("looly.centos");

		ftp.cd("/file/aaa");
		Console.log(ftp.pwd());

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void uploadTest() {
		Ftp ftp = new Ftp("localhost");

		boolean upload = ftp.upload("/temp", FileUtil.file("d:/test/test.zip"));
		Console.log(upload);

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void reconnectIfTimeoutTest() throws InterruptedException {
		Ftp ftp = new Ftp("looly.centos");

		Console.log("打印pwd: " + ftp.pwd());

		Console.log("休眠一段时间，然后再次发送pwd命令，抛出异常表明连接超时");
		Thread.sleep(35 * 1000);

		try{
			Console.log("打印pwd: " + ftp.pwd());
		}catch (FtpException e) {
			e.printStackTrace();
		}

		Console.log("判断是否超时并重连...");
		ftp.reconnectIfTimeout();

		Console.log("打印pwd: " + ftp.pwd());

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void recursiveDownloadFolder() {
		Ftp ftp = new Ftp("looly.centos");
		ftp.recursiveDownloadFolder("/",FileUtil.file("d:/test/download"));

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void recursiveDownloadFolderSftp() {
		Sftp ftp = new Sftp("127.0.0.1", 22, "test", "test");

		ftp.cd("/file/aaa");
		Console.log(ftp.pwd());
		ftp.recursiveDownloadFolder("/",FileUtil.file("d:/test/download"));

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void downloadTest() {
		Ftp ftp = new Ftp("localhost");

		List<String> fileNames = ftp.ls("temp/");
		for(String name: fileNames) {
			ftp.download("",
					name,
					FileUtil.file("d:/test/download/" + name));
		}
	}

	@Test
	@Ignore
	public void isDirTest() throws Exception {
		try (Ftp ftp = new Ftp("127.0.0.1", 21)) {
			Console.log(ftp.pwd());
			ftp.isDir("/test");
			Console.log(ftp.pwd());
		}
	}

	@Test
	@Ignore
	public void existSftpTest() throws Exception {
		try (Sftp ftp = new Sftp("127.0.0.1", 22, "test", "test")) {
			Console.log(ftp.pwd());
			Console.log(ftp.exist("/home/test"));
			Console.log(ftp.exist("/home/test/"));
			Console.log(ftp.exist("/home/test//////"));
			Console.log(ftp.exist("/home/test/file1"));
			Console.log(ftp.exist("/home/test/file1/"));
			Console.log(ftp.exist("///////////"));
			Console.log(ftp.exist("./"));
			Console.log(ftp.exist("./file1"));
			Console.log(ftp.pwd());
		}
	}

	@Test
	@Ignore
	public void existFtpTest() throws Exception {
		try (Ftp ftp = new Ftp("127.0.0.1", 21)) {
			Console.log(ftp.exist(null));
			Console.log(ftp.exist(""));
			Console.log(ftp.exist("."));
			Console.log(ftp.exist(".."));
			Console.log(ftp.exist("/"));
			Console.log(ftp.exist("/test"));
			Console.log(ftp.exist("/test/"));
			Console.log(ftp.exist("/test//////"));
			Console.log(ftp.exist("/test/.."));
			Console.log(ftp.exist("/test/."));
			Console.log(ftp.exist("/file1"));
			Console.log(ftp.exist("/file1/"));
			Console.log(ftp.exist("///////////"));
			Console.log(ftp.exist("./"));
			Console.log(ftp.exist("./file1"));
			Console.log(ftp.exist("./2/3/4/.."));
			Console.log(ftp.pwd());
		}
	}
}
