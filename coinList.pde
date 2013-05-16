
class coinList extends ArrayList<coin> {

  coinList() {
   //this = new ArrayList<coin>();
  }
  
  void updateMe() {
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
