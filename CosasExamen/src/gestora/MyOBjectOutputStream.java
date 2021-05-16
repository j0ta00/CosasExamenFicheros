package gestora;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyOBjectOutputStream extends ObjectOutputStream{
	
	protected void writeStreamHeader() throws IOException{
		
	}
	
	public MyOBjectOutputStream  () throws IOException{
		super();
	}
	
	public  MyOBjectOutputStream(OutputStream out) throws IOException{
		super(out);
	}

}
