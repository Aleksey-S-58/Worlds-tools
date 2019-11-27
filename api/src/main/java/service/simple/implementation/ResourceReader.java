package service.simple.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;

/**
 * This abstract class provides the basic methods 
 * that allows to read data from file.
 * @author Aleksey Shishkin
 *
 */
public abstract class ResourceReader {

	protected Logger logger;
	protected ServletContext context;

	protected InputStream getStream(String name) {
		InputStream stream = context.getResourceAsStream(name);
		if (stream == null ) {
			stream = context.getClassLoader().getResourceAsStream(name);
		}
		return stream;
	}

	protected byte[] getBytes(List<Byte> bytes) {
		byte[] result = new byte[bytes.size()];
		int i = 0;
		for (byte b : bytes) {
			result[i] = b;
			i++;
		}
		return result;
	}

	protected byte[] readFile(String name) throws IOException {
		InputStream stream = getStream(name);
		int available = stream.available();
		byte[] buffer = new byte[available];
		LinkedList<Byte> bytes = new LinkedList<Byte>();
		logger.debug("<----- Begin to read file {} ----->", name);
		while (available != 0) {
			int k = stream.read(buffer);
			for (int i = 0; i < k; i++)
				bytes.add(buffer[i]);
			available = stream.available();
			buffer = new byte[available];
		}
		stream.close();
		return getBytes(bytes);
	}

}
