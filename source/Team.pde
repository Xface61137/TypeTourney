Comparator<Double> dmgComp = new Comparator<Double>() {
  int compare(Double d1, Double d2) {
    if (d1 > d2) {
      return 1;
    }
    if (d2 > d1) {
      return -1;
    }
    return 0;
  }
};
class Team {
  Pkmn [] pkmn =  new Pkmn[6]; //pokemon list
  double id; //random value for matchmaking
  int score = 0; //win count

  Team(Pkmn a, Pkmn b, Pkmn c, Pkmn d, Pkmn e, Pkmn f) {
    pkmn[0] = a;
    pkmn[1] = b;
    pkmn[2] = c;
    pkmn[3] = d;
    pkmn[4] = e;
    pkmn[5] = f;
    id = random(1);
  }

  Team(Team t) {
    pkmn = t.pkmn;
    score = t.score;
  }

  boolean larger(Team t) { 
    if (getVal() > t.getVal()) {
      return true;
    }
    return false;
  }

  double pkmnvTeam(Pkmn p, double[][] tc) { 
    int type1 = p.t1;
    int type2 = (p.t2 == 18? p.t1 : p.t2);

    ArrayList<Double> power = new ArrayList<Double>();
    for (int i = 0; i < 6; i++) {
          ArrayList<Double> movePow = new ArrayList<Double>();
      for (int j = 0; j < 4; j++) {

        /*double pow = 0;
         pow += tc[pkmn[i].t1][type1]*tc[pkmn[i].t2][type1];
         pow += tc[pkmn[i].t1][type2]*tc[pkmn[i].t2][type2];
         pow += Math.max(tc[pkmn[i].t1][type1]*tc[pkmn[i].t2][type1],tc[pkmn[i].t1][type2]*tc[pkmn[i].t2][type2]);*/
        /*double pow = Math.max(tc[pkmn[i].t1][type1]*tc[pkmn[i].t2][type1], tc[pkmn[i].t1][type2]*tc[pkmn[i].t2][type2]);
         pow *= Math.min(tc[pkmn[i].t1][type1]*tc[pkmn[i].t2][type1], tc[pkmn[i].t1][type2]*tc[pkmn[i].t2][type2])+1;*/
        double mPow = tc[pkmn[i].t1][p.moveset[j]]*tc[pkmn[i].t2][p.moveset[j]];
        if (p.moveset[j] == p.t1 || p.moveset[j] == p.t2) {
          mPow *= 1.5;
        }
        movePow.add(mPow);
      }
      movePow.sort(dmgComp);
      double pow = 0;
      for(int j = 0; j < movePow.size(); j++){
        pow += (j+1)*movePow.get(j);
      }

      if (i == 0) {
        power.add(pow);
      } else {
        boolean added = false;
        for (int j = 0; j < power.size(); j++) {
          if (!added && power.get(j) > pow) {
            power.add(j, pow);
            added = true;
          }
        }
        if (! added) {
          power.add(pow);
        }
      }
    }
    double adv = 0;
    for (int i = 0; i < 6; i++) {
      adv += (6-i)*power.get(i);
    }
    //power = Math.pow(power, 3);
    return adv;
  }

  double getVal() {
    return id+score;
  }

  String toString() {
    String s = "";
    for (int i = 0; i < 6; i++) {
      s+= pkmn[i].t1 +" "+pkmn[i].t2+" ";
    }

    return s;
  }
}
