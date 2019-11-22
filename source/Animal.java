public class Animal
{
    // all characteristics of an animal
    String name, genus, sound, habitat, food;
    
    String getName(){
        return name;
    }
    
    void setName(String n){
        name = n;
    }
    
    String getGenus(){
        return this.genus;
    }
    
    void setGenus(String g){
        this.genus = g;
    }
    
    String getSound(){
        return this.sound;
    }
    
    void setSound(String s){
        this.sound = s;
    }
    
    String getHabitat(){
        return this.habitat;
    }
    
    void setHabitat(String h){
        this.habitat = h;
    }
    
    String getFood(){
        return this.food;
    }
    
    void setFood(String f){
        this.food = f;
    }
    
}