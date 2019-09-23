 import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class Valley
{
    private boolean ok;
    private boolean isVisible;
    private ArrayList<VineYard> listVinedo;
    private ArrayList<Tarp> listLonas;
    private ArrayList <Rain> lluvias;  
    private  Rectangle valleyconstructor ;
    private int realY;

    /**
     * Contructor de type VineYard Objects 
     * Crea el vaye y lo hace visible a la medida 
     * @param x tamaño en esta dimension , y tamaño en esta dimension 
     */
    
    public Valley(int x, int y){
       realY=y;
       valleyconstructor = new  Rectangle (x,y);
       valleyconstructor.changeColor("green");
       valleyconstructor.makeVisible();
       listVinedo  = new ArrayList<VineYard>();  
       listLonas = new ArrayList<Tarp>();
       lluvias = new ArrayList<Rain> ();

       
    }
    /**
     * Abre un nuevo VineYard teniendo en cuenta que no pueden estar uno encima del otro y ademas que tienen que tener nombre diferentes
     * crea un ArrayList de posiciones para el VineYard y uno para objetos de tipo VineYard
     * @param  name nombre del VineYard, xi posicion en X inicial , xf posicion en X final 
     */
    public void openVineyard(String name, int xi, int xf){  
        boolean flag;
        flag=true;
        for (int i=0; i<listVinedo.size(); i++){
            if(((listVinedo.get(i).getInicio()<=xi) && xi<=listVinedo.get(i).getFin())||( (listVinedo.get(i).getFin()<=xf) && (xf<=listVinedo.get(i).getFin()))){
                System.out.println("Operacion invalida debido a existencia de un viñedo en esta posicion");
                flag=false; 
            }
        } 
        for (int j=0; j<listVinedo.size(); j++){
            if(listVinedo.get(j).getName().equals(name)){
                System.out.println("No se puede crear el viñedo,Ya existe uno con este nombre");
                flag=false;
            }
        }
        
        if (flag || listVinedo.size()==0) {
            VineYard vinedo = new VineYard(name,xi,xf,realY,true);
            listVinedo.add(vinedo);                           
        }
    }
              

    /**
     * Elimina un VineYard completamente, de su lista de VineYards y su lista de cordenadas ademas lo vuelve invisible
     */
    public void closeVineyard(String name){        
        for (int i=0; i < listVinedo.size(); i++ ){
            if(listVinedo.get(i).getName().equals(name)){
                listVinedo.get(i).makeInvisible(); 
                listVinedo.remove(i);
            }
        }      
    }
    /**
     * Crea una lona o Tarp 
     * @param lowerEnd coordenadas X1, Y1, higherEnd coordenadas X2, Y2 
     */
    public void addTrap(int[] lowerEnd, int[] higherEnd){
       boolean sePuede = true;
       Tarp lona; 
       for(Tarp lonas: listLonas){
           if((lonas.getPuntoUno()[0] == lowerEnd[0] && lonas.getPuntoUno()[1] == lowerEnd[1])||(lonas.getPuntoUno()[0] == higherEnd[0] &&lonas.getPuntoUno()[1] == higherEnd[1])){
                System.out.println("No se puede añadir una lona en esta posicion ya existe en este punto"); 
                sePuede = false;               
           }
       }
       if(sePuede || listLonas.size() == 0){
           lona = new Tarp(lowerEnd, higherEnd,realY,true);
           listLonas.add(lona);
           //ecuacion(lowerEnd, higherEnd);
       }
    }
            
    
       /**
     * Elimina una trampa en orden de creacion o posicion en arreglo 
     * @param int position a borrar 
     */
  
    public void removeTrap(int position){
        listLonas.get(position-1).makeInvisible(); 
        listLonas.remove(position-1);
    }
    /**
     * Realiza un hueco en una posicion Y dada y en un numero de lona dado 
     * @param trap o lona, x coordenada o distancia 
     */
    public void makePuncture(int trap, int x){
        Puncture hueco;
        Tarp lona=listLonas.get(trap);
        lona.hacerHueco(x,realY);
    }
    /**
     * Elimina o parcha un hueco realizado dada una lona y una coordeanda lona 
     * @param trap o lona, position, coordeanda x 
     */
    public void patchPuncture(int trap, int position){
        Tarp lona = listLonas.get(trap);
        lona.parcharHueco(position);
        
        
    }
    /**
     * Cuando se ejecuta este metodo empieza a llover 
     */
    public void startRain(int x){
        int j=0; 
        for (int i=0; i< listLonas.size();i++){                   
            while  (j!= realY ){ 
                if (x <= listLonas.get(i).getPuntoDos()[0] && x >= listLonas.get(i).getPuntoUno()[0]){
                    float k= (listLonas.get(i).getPendiente()*x)+listLonas.get(i).getPuntoCorte();
                    //System.out.println(k+" "+j);
                    if (realY-j==(int) k){
                        ArrayList<Puncture> huecos = new ArrayList<Puncture>();
                        huecos = listLonas.get(i).getHuecos(); 
                        for(int m=0;m<huecos.size();m++){
                            if(huecos.get(m).getXPos() == x && huecos.get(m).getYPos() == j){
                                j = j+1;
                                Rain lluvia = new Rain(x,j);
                            }
                            
                        
                        
                        //System.out.println(listLonas.get(i).getPendiente());
                        else if ((int) listLonas.get(i).getPendiente()>0){
                             //System.out.println("pendiente positiva");  
                             //System.out.println(x+""+j); 
                             x=x-1;
                             j=j-1;
                             Rain lluvia = new Rain (x,j);
                        }
                        else{
                            //System.out.println("pendiente negativa");  
                            x=x+1;
                            j=j-1;
                            Rain lluvia = new Rain (x,j);  
                        }
                    }
                    }
                    else{
                        j=j+1;
                        Rain lluvia = new Rain (x,j);
                    }
                        
                }
                else if(i<listLonas.size()-1 && (x<= listLonas.get(i+1).getPuntoDos()[0] && x >= listLonas.get(i+1).getPuntoUno()[0])){
                    break;
                }
                else{
                    j=j+1;
                    Rain lluvia = new Rain (x,j);
                }
                    
            }
                
                  
        }
    }
    /**
     * Cuando se invoca este metodo para la lluvia 
     */ 
    public void stopRain(int position){
      

    }
    public String[] rainFalls(){
        return new String[1];
    }
    /**
     * Vuelve Visible el objeto de tipo valle 
     */
    public void makeVisible(){
        for (int i=0; i < listVinedo.size(); i++ ){
            listVinedo.get(i).makeVisible();
        }
        for (int i=0; i < listLonas.size(); i++ ){
            listLonas.get(i).makeVisible();
        }
    }
    /**
     * Vuelve invisible el objeto de tipo valle 
     */
    public void makeInvisible(){
        for (int i=0; i < listVinedo.size(); i++ ){
            listVinedo.get(i).makeInvisible();
        }
        for (int i=0; i < listLonas.size(); i++ ){
            listLonas.get(i).makeInvisible();
        }
    
    }
    /** 
     * Finaliza la ejecucion de una simulacion 
     */
    public void finish(){
        System.exit(0);
    }
    public void ok(){
    }

    
    public void zoom (String simbolo){
        Canvas canvas= Canvas.getCanvas();
        canvas.zoom(simbolo);
    }
    
    

}
 