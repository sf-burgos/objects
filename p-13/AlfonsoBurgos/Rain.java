import java.util.ArrayList;
import java.util.Arrays;
/**
 * Write a description of class Rain here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rain
{
    boolean flagRain;
    Rectangle rain;    
    public static int lluviaX;
    /**
     * Constructor for objects of class Rain
     */
     //public Rectangle(int newHeight, int newWidth, int newXPosition, int newYPosition,String newColor ){
    public Rain (int x, int y){        
        rain = new  Rectangle (1,1,x,y,"blue");
        rain.changeColor("blue");
        flagRain=true;
        rain.makeVisible();   
      
    }

    public void startRain(int x,ArrayList<Tarp> listLonas,int realY){
        int j=0; 
        for (int i=0; i< listLonas.size();i++){                   
            while  (j!= realY ){    
                if (listLonas.get(i).verificarHueco(x,j)){
                    j=j+1;Gota lluvia = new Gota (x,j);break;
                }else{
                    if (x <= listLonas.get(i).getPuntoDos()[0] && x >= listLonas.get(i).getPuntoUno()[0]){                   
                        float k= (listLonas.get(i).getPendiente()*x)+listLonas.get(i).getPuntoCorte();  
                        if (realY-j==(int) k){                                                                                                                                              
                            if ((float) listLonas.get(i).getPendiente()>0){
                                 x=x-1;j=j-1;Gota lluvia = new Gota (x,j);
                            
                            }else{
                                x=x+1; j=j-1;
                                Gota lluvia = new Gota (x,j);  
                        }
                        }else{
                            j=j+1;Gota lluvia = new Gota (x,j);
                        }                            
                    }else if(i<listLonas.size()-1 && (x<= listLonas.get(i+1).getPuntoDos()[0] && x >= listLonas.get(i+1).getPuntoUno()[0]) && !(listLonas.get(i+1).verificarHueco(x,j))){
                        break;
                    }else{
                        j=j+1;Gota lluvia = new Gota (x,j);
                    }
                }                
            }            
        }
        while  (j!= realY ){
                j=j+1;Gota lluvia = new Gota (x,j);
        }
        lluviaX=x;
    }
    
}
