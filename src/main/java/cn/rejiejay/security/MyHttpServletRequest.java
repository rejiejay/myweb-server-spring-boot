package cn.rejiejay.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 在过滤器或拦截器中需要传HttpServletRequest的地方 传MyHttpServletRequest(HttpServletRequest request)
 * getInputStream()和getReader()
 * 都只能读取一次，由于RequestBody是流的形式读取，那么流读了一次就没有了，所以只能被调用一次。 先将RequestBody保存，然后通过
 * _Servlet 自带的HttpServletRequestWrapper类覆盖getReader()和getInputStream()方法，
 * 使流从保存的body读取。然后再Filter中将ServletRequest替换为ServletRequestWrapper
 */
public class MyHttpServletRequest extends HttpServletRequestWrapper {
	private String requestBody = null;

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public MyHttpServletRequest(HttpServletRequest request) throws IOException {
		super(request);
		if (requestBody == null) {
			requestBody = readBody(request);
		}
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new CustomServletInputStream(requestBody);
	}

	private static String readBody(ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		String inputLine;
		BufferedReader br = null;
		try {
			br = request.getReader();
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to read body.", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return sb.toString();
	}

	 
	private class CustomServletInputStream extends ServletInputStream {
		private ByteArrayInputStream buffer;
 
		public CustomServletInputStream(String body) {
			body = body == null ? "" : body;
			this.buffer = new ByteArrayInputStream(body.getBytes());
		}
 
		@Override
		public int read() throws IOException {
			return buffer.read();
		}
 
		@Override
		public boolean isFinished() {
			return buffer.available() == 0;
		}
 
		@Override
		public boolean isReady() {
			return true;
		}
 
		@Override
		public void setReadListener(ReadListener listener) {
			throw new RuntimeException("Not implemented");
		}
	}
}