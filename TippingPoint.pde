

PImage[] imgCoins;
PImage[][] imgPeople;

hand myHand;
coinList coins;
people servers, clients;

void setup() {
  size(displayWidth, displayHeight);
  background(255);
  stroke(0);
  noCursor();
  
  myHand = new hand();
  coins = new coinList();
  clients = new people(false);
  servers = new people(true);
  
  imgCoins = new PImage[8];
  imgPeople = new PImage[2][3];
  
  imgCoins[0] = loadImage("data/coin_001.png");
  imgCoins[1] = loadImage("data/coin_002.png");
  imgCoins[2] = loadImage("data/coin_005.png");
  imgCoins[3] = loadImage("data/coin_010.png");
  imgCoins[4] = loadImage("data/coin_020.png");
  imgCoins[5] = loadImage("data/coin_050.png");
  imgCoins[6] = loadImage("data/coin_100.png");
  imgCoins[7] = loadImage("data/coin_200.png");
  
  imgPeople[0][0] = loadImage("data/head_a_0.png");
  imgPeople[0][1] = loadImage("data/head_a_1.png");
  imgPeople[0][2] = loadImage("data/head_a_2.png");
  
  imgPeople[1][0] = loadImage("data/head_b_0.png");
  imgPeople[1][1] = loadImage("data/head_b_1.png");
  imgPeople[1][2] = loadImage("data/head_b_2.png");
  
}



void draw() {
 background(255);
 
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


void keyPressed() {
  
 if(key == CODED) {
  if(keyCode == LEFT)  myHand.gVx = true;
  if(keyCode == RIGHT) myHand.dVx = true;
 }
 
 if(key == ' ') {
     coins.add( new coin(myHand.x+myHand.l/2) );
 }
 
}
 
void keyReleased() {
  
 if(key == CODED) {
  if(keyCode == LEFT)  myHand.gVx = false;
  if(keyCode == RIGHT) myHand.dVx = false;
 }
  
} 
