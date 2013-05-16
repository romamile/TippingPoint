import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TippingPoint extends PApplet {




//TO DO:
// 1) Penser \u00e0 l'association et r\u00e9partition entre visages et clients/servers


PImage[] imgCoins;
PImage[][] imgPeople;

hand myHand;
coinList coins;
people servers, clients;

public void setup() {
  size(displayWidth, displayHeight);
  background(0);
  stroke(0);
  noCursor();
  
  myHand = new hand();
  coins = new coinList();
  clients = new people(false);
  servers = new people(true);
  
  imgCoins = new PImage[8];
  imgPeople = new PImage[2][3];
  
  imgCoins[0] = loadImage("data/coin_1cent.png");
  imgCoins[1] = loadImage("data/coin_2cent.png");
  imgCoins[2] = loadImage("data/coin_5cent.png");
  imgCoins[3] = loadImage("data/coin_10cent.png");
  imgCoins[4] = loadImage("data/coin_20cent.png");
  imgCoins[5] = loadImage("data/coin_50cent.png");
  imgCoins[6] = loadImage("data/coin_1euro.png");
  imgCoins[7] = loadImage("data/coin_2euro.png");
  
  imgPeople[0][0] = loadImage("data/waiter_1_neutral.png");
  imgPeople[0][1] = loadImage("data/waiter_1_happy.png");
  imgPeople[0][2] = loadImage("data/waiter_1_sad.png");
  
  imgPeople[1][0] = loadImage("data/waiter_2_neutral.png");
  imgPeople[1][1] = loadImage("data/waiter_2_happy.png");
  imgPeople[1][2] = loadImage("data/waiter_2_sad.png");
  
}



public void draw() {
 background(0);
 
 //UPDATE triangle

 
 myHand.updateMe(); myHand.drawMe();
 coins.updateMe();  coins.drawMe();
 clients.updateMe();  clients.drawMe();
 servers.updateMe();  servers.drawMe();
 
 
 //Interaction coin and people, WHERE DO YOU PUT THAT SHIT ?
 for(int i=0; i < coins.size(); i++) { 
  for(int j=0; j < clients.size(); j++) {
 
    if( coins.get(i).x > clients.get(j).x - clients.get(j).ww/2 - coins.get(i).r/2  &&   coins.get(i).x < clients.get(j).x + clients.get(j).ww/2 + coins.get(i).r/2
     && coins.get(i).y > clients.get(j).y - coins.get(i).r/2                        &&   coins.get(i).y < clients.get(j).y + clients.get(j).hh ) {
      clients.get(j).getHappy();
      coins.remove(i);
      if(i!=0) i--;
      break;
    }
    
  }
 }
 
  for(int i=0; i < coins.size(); i++) {
  for(int j=0; j < servers.size(); j++) {
 
    if( coins.get(i).x > servers.get(j).x - servers.get(j).ww/2  - coins.get(i).r/2  &&   coins.get(i).x < servers.get(j).x + servers.get(j).ww/2 + coins.get(i).r/2
     && coins.get(i).y > height-80 - coins.get(i).r/2                                &&   coins.get(i).y < height-80 + 70 ) {
       
      servers.get(j).getHappy();
      coins.remove(i);
      if(i!=0) i--;
      //myPeople.servers.remove(j);
      break;
    }
    
  }
 }
 
}


public void keyPressed() {
  
 if(key == CODED) {
  if(keyCode == LEFT)  myHand.gVx = true;
  if(keyCode == RIGHT) myHand.dVx = true;
 }
 
 if(key == ' ') {
     coins.add( new coin(myHand.x+myHand.l/2) );
 }
 
}
 
public void keyReleased() {
  
 if(key == CODED) {
  if(keyCode == LEFT)  myHand.gVx = false;
  if(keyCode == RIGHT) myHand.dVx = false;
 }
  
} 
class coin{
 float x,y,vy,ay;
 int value;
 int r;
 
 coin(float _x) {
   x = _x;
   vy = 5; ay = 0;
   y = 50;
   
   value= PApplet.parseInt(random(0,8));
 }
  
 coin(float _x, int v) {
   x = _x;
   vy = 0; ay = 0;
   y = 50;
   
   value=v;
 }
 
 public boolean updateMe() {
  ay+=0.03f;
  vy+=ay;
  if(vy > 50) vy = 50;
  y+=vy;
  if(y>height)
    return false;
  return true;
 }
 
 public void drawMe() {
  switch(value) {
    case 0: r = 25; break;
    case 1: r = 30; break;
    case 2: r = 35; break;
    case 3: r = 35; break;
    case 4: r = 40; break;
    case 5: r = 45; break;
    case 6: r = 40; break;
    case 7: r = 50; break;
    
  }
  r*=2;
  image(imgCoins[value], x-r/2, y, r, r);
  //ellipse(x, y, r, r);
 } 
  
  
}

class coinList extends ArrayList<coin> {

  coinList() {
   //this = new ArrayList<coin>();
  }
  
  public void updateMe() {
    for(int i=0; i < size(); ) {
      if(get(i).updateMe()) {
        i++;
      } else {
        remove(i);
      }
    }
        
    
  }
  
  public void drawMe() { 
    for(int i=0; i < size(); i++)
      get(i).drawMe();
  }
}
class hand {
  int l;
  float x, vx, ax;
  boolean gVx, dVx;

  hand() {   
    l=50;
    x = width/2;
    vx=0;
    ax=0;
    gVx = false; dVx = false;
  }
  
  public void updateMe() {
   if(gVx && !dVx) vx-=0.5f;
   if(dVx && !gVx) vx+=0.5f;
   if(vx >  15) vx =  15;
   if(vx < -15) vx = -15;
      if(x > width - l) {vx = -abs(vx); vx *=0.9f; ax=0;}
      if(x < 0)         {vx = abs(vx); vx *=0.9f; ax=0;}
   vx *= 0.95f;
   //vx += ax;
   x  += vx;
    
  }
  
  public void drawMe() {
   fill(200,40,40);
   pushMatrix();
     translate(x, 10);
     triangle(0, 10, l, 10, l/2, l);
   popMatrix();
    
  }
  
}
class people extends ArrayList<person> {
 boolean serverOrClient;
  
 people(boolean _type) {
  // this = new ArrayList<person>();
   serverOrClient = _type;
   if(serverOrClient)
       newPerson();
   else
     for(int i=0; i<4; i++)
       newPerson();
 }
 
 
 public boolean newPerson() {
   int count = 0;
   boolean notOk = true;
   float fX = random(80, width-80);
   
   while(notOk) {
     if(count++ > 20)
       return false;
       
     notOk = false;
     fX = random(80, width-80);
     for(int i=0; i < size(); i++)
       if(abs(get(i).x - fX) < 55)
         notOk = true;
     
   }
   add( new person(serverOrClient, 100, PApplet.parseInt(random(0,2)), fX) );
   return true;
 }
 
 
 public void updateMe() {
   if(serverOrClient)
     if(PApplet.parseInt(random(0,500)) == 1 && size() < 10)
       newPerson();
       
   
   float k = 0.2f;
   float a,b,c;
   
   //check for bumps: 
   for(int i=0; i < size(); i++)
     for(int j=i+1; j < size(); j++)
       if( abs(get(i).x - get(j).x) < get(j).ww) {
       
           a = get(i).vx;
           b = get(j).vx;
           get(i).vx = -k*a + (1-k)*b;
           get(j).vx = -k*b + (1-k)*a;
           
           if(get(i).x > get(j).x) c = 1; else c = -1;
           get(i).x = get(i).x + c*(get(j).ww - abs(get(i).x - get(j).x))/2;
           get(j).x = get(j).x - c*(get(j).ww - abs(get(i).x - get(j).x))/2;    
       } 
   
   for(int i=0; i < size(); ) {
     if(get(i).updateMe()) {
       i++;
     } else {
       remove(i);
     }
   }

 }
 
 public void drawMe() {
   
   for(int i=0; i < size(); i++)
     get(i).drawMe();
   
 }
  
  
}
class person {
 int t;
 int ww, hh;
 int thune;
 boolean serverOrClient;
 float x, vx, y;
 int mood, tMood;
 int type;

  person(boolean _serverOrClient, int _thune, int _type, float _x) {
    ww = 100;
   hh = 150;
    serverOrClient = _serverOrClient;
    type = _type;
    thune = _thune;
   x = _x;
   vx = random(-10,10);
   t = 0;
   mood = 0;   tMood = 0;
   
    if(serverOrClient) y=height-hh+10; else  y=300;
  }
  
  public void getHappy() {
    mood = 1;
    tMood = 60;
  }

  public void getSad() {
    mood = 2;
    tMood = 60;
    
  }
  
  public boolean updateMe() {
    
   //Deal with the mood 
   tMood--;
   if(tMood < 0) {
     tMood=0;
     mood=0;
   }
   
    
    //Bumpe into walls
    if(x > width - ww/2) {vx = -abs(vx); vx *=0.9f; }
    if(x < ww/2)         {vx = abs(vx); vx *=0.9f; }

    //Change directon randomly
    t++;
    if (random(0,1000) < t) { t=0; vx = random(-5,5); }
    

    x += vx;

    return true;
  }
  
  public void drawMe() {
    pushMatrix();
      translate(x - ww/2, y);
      image(imgPeople[type][mood], 0,0,ww,hh);
    popMatrix();
  }
           
} 
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TippingPoint" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
