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
  
  void getHappy() {
    mood = 1;
    tMood = 60;
  }

  void getSad() {
    mood = 2;
    tMood = 60;
    
  }
  
  boolean updateMe() {
    
   //Deal with the mood 
   tMood--;
   if(tMood < 0) {
     tMood=0;
     mood=0;
   }
   
    
    //Bumpe into walls
    if(x > width - ww/2) {vx = -abs(vx); vx *=0.9; }
    if(x < ww/2)         {vx = abs(vx); vx *=0.9; }

    //Change directon randomly
    t++;
    if (random(0,1000) < t) { t=0; vx = random(-5,5); }
    

    x += vx;

    return true;
  }
  
  void drawMe() {
    pushMatrix();
      translate(x - ww/2, y);
      image(imgPeople[type][mood], 0,0,ww,hh);
    popMatrix();
  }
           
} 
