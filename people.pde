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
 
 
 boolean newPerson() {
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
   add( new person(serverOrClient, 100, int(random(0,2)), fX) );
   return true;
 }
 
 
 void updateMe() {
   if(serverOrClient)
     if(int(random(0,500)) == 1 && size() < 10)
       newPerson();
       
   
   float k = 0.2;
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
 
 void drawMe() {
   
   for(int i=0; i < size(); i++)
     get(i).drawMe();
   
 }
  
  
}
