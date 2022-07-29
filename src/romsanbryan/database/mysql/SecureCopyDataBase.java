package romsanbryan.database.mysql;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SecureCopyDataBase {

	public static void main(String[] args) {

		String pathMysqlDump = "/opt/lampp/bin/mysqldump"; // Inside mysql dir
		String host = "localhost";
		String port = "3307";
		String user = "root";
		String pass = "";
		String tableName = "test";
		String[] databaseName = { "table1", "table2" }; // can empty

		String pathSecure = "a.sql";

		StringBuilder conection = new StringBuilder();
		Process process = null;
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;

		conection.append(pathMysqlDump);
		conection.append(" -h").append(host);
		conection.append(" -P").append(port);
		conection.append(" -u").append(user);

		if (!pass.isEmpty())
			conection.append(" -p").append(pass);

		conection.append(" ").append(tableName);

		for (int i = 0; i < databaseName.length; i++) {
			conection.append(" ").append(databaseName[i]);
		}
		System.out.println(conection.toString());

		try {
			process = Runtime.getRuntime().exec(conection.toString());

			inputStream = process.getInputStream();
			fileOutputStream = new FileOutputStream(pathSecure);
			
			byte[] buffer = new byte[1000];
			int leido = inputStream.read(buffer);
			while (leido > 0) {
				fileOutputStream.write(buffer, 0, leido);
				leido = inputStream.read(buffer);
			}
		} catch (IOException e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			if (process != null && process.isAlive()) {
				process.destroyForcibly();
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.err.println("Can't close inputStream: " + e.getMessage());
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					System.err.println("Can't close fileOutputStream: " + e.getMessage());
				}
				
			}
			System.out.println("Process finished");
		}
	}

}
