package dominio;

import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String LOS_LIBROS_PALINDROMOS_NO_SE_PRESTAN ="los libros palíndromos solo se pueden utilizar en la biblioteca";

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

	public void prestar(String isbn, String nombreUsuario) {
		if(!palindromo(isbn)) {
			if(!esPrestado(isbn)) {	
				Prestamo prestamo= new Prestamo(new Date(),this.repositorioLibro.obtenerPorIsbn(isbn),calcularFechaEntrega(isbn),nombreUsuario);
				repositorioPrestamo.agregar(prestamo);
			}else {
			throw new  PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
			}		
		}else {
		throw new  PrestamoException(LOS_LIBROS_PALINDROMOS_NO_SE_PRESTAN);	
		}
	}

	public boolean esPrestado(String isbn) {		
		return this.repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn)!=null;
	}
	
	  static boolean palindromo(String isbn)
	    {
	        int i=0;
	        while (i < isbn.length() / 2)
	        {
	            if (isbn.charAt(i) != isbn.charAt(isbn.length() -1 -i))
	                return false;
	            i++;
	        }
	        return true;
	    }
	  
	  public static Date calcularFechaEntrega(String isbn) {
			char [] cadena= isbn.toCharArray();
			int suma=0;
			for(int i=0; i<cadena.length;i++) {
				 if (Character.isDigit(cadena[i])) {
					 int numero= Integer.parseInt(""+cadena[i]);	
					 suma= suma+numero;
				 }
			}
			return sumarDiasFecha(suma); 
		}
	  
	  public static Date sumarDiasFecha(int suma){
	      if (suma<30) return null;
//	      int cantidadDomingos=0;
	      Date fecha =new Date();
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); 
	      calendar.add(Calendar.DAY_OF_YEAR, 16);
	      System.out.println(calendar.DAY_OF_WEEK+"domingo");
	      if(calendar.DAY_OF_WEEK==7) {
		      calendar.add(Calendar.DAY_OF_YEAR, 1);
	      }
	      return calendar.getTime(); 
	}
}
