class Pkmn {
  int t1;
  int t2;
  int teams;
  double topTeams;
  double topCut;
  int wins;
  double avg;
  double toppct;
  double cutpct;
  double[] moves = new double[18];
  int[] moveset = new int[4];
  int[][] cMoves = new int[18][2];


  int cUsage;
  boolean setup = true;

  public Pkmn(int t1, int t2) {
    this.t1 = t1;
    this.t2 = t2;
    for(int i = 0; i < 18; i++){
      moves[i] = 0.01;
      cMoves[i][0] = i;
    }
  }
  
  public Pkmn(Pkmn p){
    this.t1 = p.t1;
    this.t2 = p.t2;
    this.moves = p.moves;
  }

  public boolean equals(Pkmn p) {
    return p.t1 == t1 && p.t2 == t2;
  }

  public void calc() {
    if (teams == 0) {
      toppct = 0;
      avg = 0;
      cutpct = 0;
    } else {
      toppct = (double)topTeams/teams;
      avg = (double)wins/teams;
      cutpct = topCut/cutteams*3;
    }
  }

  public void reset() {
    wins = 0;
    teams = 0;
    topTeams = 0;
    topCut = 0;
    moves = new double[18];
    for(int i = 0; i < 18; i++){
      moves[i] = 0.01;
    }
  }

  public double usage() {
    if (setup) { 
      return 1;
    }
    return Math.pow(Math.max(Math.log((double)teams/part*1000+1)*(Math.pow(toppct/mTop, 2)*4+Math.pow(cutpct/mCut, 2)), 0.1),1);
  }

  public double rawUsage() {
    return (double)teams/part;
  }
  
  public double vPkmn(Pkmn p){
    double pow = 0;
    int type1 = p.t1;
    int type2 = (p.t2 == 18? p.t1 : p.t2);
    pow += tc[t1][type1]*tc[t2][type1];
    pow += tc[t1][type2]*tc[t2][type2];
    pow += Math.max(tc[t1][type1]*tc[t2][type1],tc[t1][type2]*tc[t2][type2]);
    return pow;
  }
}
