import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.jws.*;
import java.io.*;
import java.net.*;

public class AnimalList extends JFrame
{   
    Animal[] an = null;
    int n_animals = 0;
    String[] names = null;
    JButton[] buttons = null;
    JPanel centerPanel = null;
    
    public AnimalList(){
        // GUI setup
        super("ACME");
        setBounds(0,0,400,400);
        Font font=new Font("ARIAL",0,48);
    }
    
    public Animal[] retrieve(){
        Animal[] animals = null;
        
        // a site will be asked for information
        URL url;
        InputStream instream0;
        InputStream instream1;
        BufferedReader buffread0;
        BufferedReader buffread1;
        String line;
        
        short a = 0;
        int count = 0;
        
        try{
            
            url = new URL("https://internship.jamfresearch.com/api/animals");
            instream0 = url.openStream();
            buffread0 = new BufferedReader(new InputStreamReader(instream0));
            
            // find the line
            while((line = buffread0.readLine()) != null){
                if(line.contains("animals")){
                    break;
                }
            }
            
            // every set of 2 quotation marks indicates an word
            count = (occurances(line, '"'))/2;
            
            // get the names of the available animals
            names = parseFromLine(line, count);
            
            // get the characteristics of all of the animals
            animals = setCharacteristics(names);
            
        } catch(MalformedURLException mue){
            System.out.println("you've got a bad URL!");
        } catch(IOException ioe){
            System.out.println("something went wrong in IO!");
        }
        
        return animals;
        
    }
    
    // set up each animal with all of its characteristics
    public Animal[] setCharacteristics(String[] n){
        URL url;
        InputStream instream;
        BufferedReader buffread;
        String line = "";
        
        // an array of animals waiting to be filled in
        Animal[] animals = new Animal[n.length + 1];
        
        // start from one since n[0] = "animal"
        for(int l = 1; l < n.length; l++){
            try{
                // look at the proper address
                url = new URL("https://internship.jamfresearch.com/api/animals?animal=" + n[l]);
                instream = url.openStream();
                buffread = new BufferedReader(new InputStreamReader(instream));
                line = buffread.readLine();
                //System.out.println(line);
            } catch(MalformedURLException mue){
                System.out.println("you've got a bad URL!");
            } catch(IOException ioe){
                System.out.println("something went wrong in IO!");
            }
            
            // 11 words in quotation marks per line. we'll pick and choose which we want
            String[] parsed_line = parseFromLine(line, 11);
            
            // choose the words from the places we want
            animals[l-1] = new Animal();
            
            animals[l-1].setName(parsed_line[1]);
            animals[l-1].setGenus(parsed_line[4]);
            animals[l-1].setSound(parsed_line[6]);
            animals[l-1].setHabitat(parsed_line[8]);
            animals[l-1].setFood(parsed_line[10]);
            n_animals++;
            
            System.out.println(animals[l-1].getHabitat());
        }
        
        //animals[3] = new Animal();
        //animals[3].setName("beep");
        System.out.println("here : " + animals[1].getName());
        setupGUI(animals);
        
        an = animals;
        return animals;
        
    }
    
    // check string s for occurances of c
    public int occurances(String s, char c){
        int x = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == c){
                x++;
            }
        }
        x++;
        return x;
    }
    
    // get names from line
    public String[] parseFromLine(String s, int x){
        String name = "";
        int n = 0;
        int i = 0;
        int odd = 0b0;
        int mask = 0b1;
        
        String[] a = new String[x];
        
        for(int j = 0; j < s.length(); j++){
            //System.out.println("j: " + j + " and the char here: " + s.charAt(j));
            if(s.charAt(j) == '"'){
                // a quotation mark means the beginning or end of an animal name
                odd ^= mask;
                // n keeps track of what quotation mark we're on, therefore our spot in the String array
                n++;;
                name = "";
            }else if(odd == 1){
                // enter the animal's name letter by letter
                // since we have twice the number of quotation marks as names in the array, div by 2
                i = n/2;
                name = name + "" + s.charAt(j);
                a[i] = name;
                //System.out.println(i);
            }
        }

        return a;
    }
    
    public void setupGUI(Animal[] animals){
        buttons = new JButton[n_animals];
        
        JLabel label = null;
        Font font=new Font("ARIAL",0,48);
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(n_animals, 1));
        
        for(int m = 0; m < n_animals; m++){
            buttons[m] = new JButton(animals[m].getName());
            label = new JLabel(animals[m].getName());
            buttons[m].setFont(font);
            centerPanel.add(buttons[m]);
            buttons[m].addActionListener(new ButtonHandler());
        }

        add(centerPanel, BorderLayout.CENTER);
    }
    
    public void loadAnimalPage(int x){
        centerPanel.removeAll();
        centerPanel.revalidate();
        centerPanel.repaint();
        
        //centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(n_animals, 1));
        
        Font font=new Font("ARIAL",0,14);
        
        JButton back = new JButton("Back");
        
        back.addActionListener(new ButtonHandler());
        
        back.setFont(font);
        
        add(back, BorderLayout.NORTH);
        
        JLabel lname = new JLabel("Name : ");
        JLabel aname = new JLabel(an[x].getName());
        JLabel lgenus = new JLabel("Genus : ");
        JLabel agenus = new JLabel(an[x].getGenus());
        JLabel lsound = new JLabel("Sound : ");
        JLabel asound = new JLabel(an[x].getSound());
        JLabel lhabitat = new JLabel("Habitat : ");
        JLabel ahabitat = new JLabel(an[x].getHabitat());
        JLabel lfood = new JLabel("Food : ");
        JLabel afood = new JLabel(an[x].getFood());
        
        lname.setFont(font);
        aname.setFont(font);
        lgenus.setFont(font);
        agenus.setFont(font);
        lsound.setFont(font);
        asound.setFont(font);
        lhabitat.setFont(font);
        ahabitat.setFont(font);
        lfood.setFont(font);
        afood.setFont(font);
        
        centerPanel.add(lname);
        centerPanel.add(aname);
        centerPanel.add(lgenus);
        centerPanel.add(agenus);
        centerPanel.add(lsound);
        centerPanel.add(asound);
        centerPanel.add(lhabitat);
        centerPanel.add(ahabitat);
        centerPanel.add(lfood);
        centerPanel.add(afood);
        
        add(centerPanel, BorderLayout.CENTER);
        
        centerPanel.revalidate();
        centerPanel.repaint();
    }
    
    public class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int n = 0; n < names.length - 1; n++){
                if(e.getSource() == buttons[n]){
                    loadAnimalPage(n);
                }
            }
            
        }
    }
    
    public static void main(String[] args){
        AnimalList al = new AnimalList();
        al.retrieve();
        al.setVisible(true);
    }
}