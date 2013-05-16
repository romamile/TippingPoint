class coin{
 float x,y,vy,ay;
 int value;
 int r;
 
 coin(float _x) {
   x = _x;
   vy = 5; ay = 0;
   y = 50;
   
   value= int(random(0,8));
 }
  
 coin(float _x, int v) {
   x = _x;
   vy = 0; ay = 0;
   y = 50;
   
   value=v;
 }
 
 boolean updateMe() {
  ay+=0.03;
  vy+=ay;
  if(vy > 50) vy = 50;
  y+=vy;
  if(y>height)
    return false;
  return true;
 }
 
 void drawMe() {
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
