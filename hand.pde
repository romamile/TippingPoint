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
  
  void updateMe() {
   if(gVx && !dVx) vx-=0.5;
   if(dVx && !gVx) vx+=0.5;
   if(vx >  15) vx =  15;
   if(vx < -15) vx = -15;
      if(x > width - l) {vx = -abs(vx); vx *=0.9; ax=0;}
      if(x < 0)         {vx = abs(vx); vx *=0.9; ax=0;}
   vx *= 0.95;
   //vx += ax;
   x  += vx;
    
  }
  
  void drawMe() {
   fill(200,40,40);
   pushMatrix();
     translate(x, 10);
     triangle(0, 10, l, 10, l/2, l);
   popMatrix();
    
  }
  
}
