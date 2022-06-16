import java.util.*; 
double[][] tc = { { 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }// nor
  , { 1, .5, 2, 1, .5, .5, 1, 1, 2, 1, 1, .5, 2, 1, 1, 1, .5, .5 }// fir
  , { 1, .5, .5, 2, 2, .5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, .5, 1 }// wat
  , { 1, 1, 1, .5, 1, 1, 1, 1, 2, .5, 1, 1, 1, 1, 1, 1, .5, 1 }// ele
  , { 1, 2, .5, .5, .5, 2, 1, 2, .5, 2, 1, 2, 1, 1, 1, 1, 1, 1 }// gra
  , { 1, 2, 1, 1, 1, .5, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1 }// ice
  , { 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, .5, .5, 1, 1, .5, 1, 2 }// fig
  , { 1, 1, 1, 1, .5, 1, .5, .5, 2, 1, 2, .5, 1, 1, 1, 1, 1, .5 }// poi
  , { 1, 1, 2, 0, 2, 2, 1, .5, 1, 1, 1, 1, .5, 1, 1, 1, 1, 1 }// gro
  , { 1, 1, 1, 2, .5, 2, .5, 1, 0, 1, 1, .5, 2, 1, 1, 1, 1, 1 }// fly
  , { 1, 1, 1, 1, 1, 1, .5, 1, 1, 1, .5, 2, 1, 2, 1, 2, 1, 1 }// psy
  , { 1, 2, 1, 1, .5, 1, .5, 1, .5, 2, 1, 1, 2, 1, 1, 1, 1, 1 }// bug
  , { .5, .5, 2, 1, 2, 1, 2, .5, 2, .5, 1, 1, 1, 1, 1, 1, 2, 1 }// roc
  , { 0, 1, 1, 1, 1, 1, 0, .5, 1, 1, 1, .5, 1, 2, 1, 2, 1, 1 }// gho
  , { 0, .5, .5, .5, .5, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2 }// dra
  , { 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 0, 2, 1, .5, 1, .5, 1, 2 }// dar
  , { .5, 2, 1, 1, .5, .5, 2, 0, 2, .5, .5, .5, .5, 1, .5, 1, .5, .5 }// ste
  , { 1, 1, 1, 1, 1, 1, .5, 2, 1, 1, 1, .5, 1, 1, 0, .5, 2, 1 }// fai
  , { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }// nul
};// type chart
String[] ts = { "nor", "fir", "wat", "ele", "gra", "ice", "fig", "poi", "gro", "fly", "psy", "bug", "roc", "gho", 
  "dra", "dar", "ste", "fai", "---" };// strings corresponding to types for displaying purposes
String[] filenames = {"normal", "fire", "water", "electric", "grass", "ice", "fighting", "poison", "ground", 
  "flying", "psychic", "bug", "rock", "ghost", "dragon", "dark", "steel", "fairy"};
PImage[] images = new PImage[18];
ArrayList<Team> teams = new ArrayList<Team>(); //List to store teams
ArrayList<Pkmn> pkmn = new ArrayList<Pkmn>();
Bracket b = new Bracket();

Comparator<Team> teamcomp = new Comparator<Team>() {
  int compare(Team t1, Team t2) {
    if (t1.score+t1.id == t2.score+t2.id) {
      return 0;
    }
    if (t1.score+t1.id > t2.score+t2.id) {
      return 1;
    }
    return -1;
  }
};

Comparator<Pkmn> pkmncomp = new Comparator<Pkmn>() {
  int compare(Pkmn p1, Pkmn p2) {
    if (p1.rawUsage() == p2.rawUsage()) {
      return 0;
    }
    if (p1.rawUsage() > p2.rawUsage()) {
      return -1;
    }
    return 1;
  }
};

Comparator<Pkmn> usagecomp = new Comparator<Pkmn>() {
  int compare(Pkmn p1, Pkmn p2) {
    if (p1.usage() == p2.usage()) {
      return 0;
    }
    if (p1.usage() > p2.usage()) {
      return -1;
    }
    return 1;
  }
};

Comparator<Pkmn> cusagecomp = new Comparator<Pkmn>() {
  int compare(Pkmn p1, Pkmn p2) {
    if (p1.cUsage == p2.cUsage) {
      return 0;
    }
    if (p1.cUsage > p2.cUsage) {
      return -1;
    }
    return 1;
  }
};

int cutteams;
int tourney = 0;
boolean stats = false;
boolean brackets = false;
boolean sim = true;
boolean tutorial = true;
int offset = 0;
int doubleTap = 0;

double mTop = 0;
double mCut = 0;

int part = 10000; //amount of teams per simulation
int games = ceil((float)(Math.log(part)/Math.log(2)))*2;//games in swiss style tournament; log2(part) placement games

void setup() {
  size(1300, 800);
  textAlign(LEFT, TOP);
  fill(0);
  //noLoop();
  for (int i = 0; i < images.length; i++) {
    images[i] = loadImage(sketchPath()+"\\data\\types\\"+filenames[i]+".gif");
  }

  for (int i = 0; i < 18; i++) {
    for (int j = 0; j <= i; j++) {
      if (i == j) {
        pkmn.add(new Pkmn(i, 18));
      } else {
        pkmn.add(new Pkmn(i, j));
      }
    }
  }
}

void draw() {
  if (!brackets || sim) {
    background(200);
  }
  if (stats) {
    background(200);
    pkmn.sort(cusagecomp);
    textSize(20);
    text("Top Types by Cumulative Usage"+(tourney == 1? " (tournament #1 discounted due to random usage stats)": ""), 30, 20);
    if (tutorial) {
      textAlign(CENTER, TOP);
      text("Press SPACE to go back to Tournament View", 650, 765);
      textAlign(LEFT, TOP);
    }

    if (tourney > 1) {
      textSize(16);
      text("Hover over a Pokemon Type to see its Cumulative Move Data", 790, 25);
    }

    textSize(12);

    for (int i = 0; i < pkmn.size(); i++) {
      image(images[pkmn.get(i).t1], 50+183*(i/25), 65+i%25*28);
      if (pkmn.get(i).t2 != 18) {
        image(images[pkmn.get(i).t2], 85+183*(i/25), 65+i%25*28);
      }
      textAlign(RIGHT, TOP);
      fill(90);
      text(""+(i+1)+")", 40+183*(i/25), 63+i%25*28);
      textAlign(LEFT, TOP);
      fill(0);
      text(pkmn.get(i).cUsage, 140+183*(i/25), 63+i%25*28);
    }
    if (mouseY == constrain(mouseY, 50, 750) && (mouseX-50)%183  ==  constrain((mouseX-50)%183, 0, 67) && (mouseY-65)%28  == constrain((mouseY-65)%28, 2, 14)) {
      if ((int)(mouseX-50)/183*25+(int)(mouseY-65)/28%25 == constrain((int)(mouseX-50)/183*25+(int)(mouseY-65)/28%25, 0, pkmn.size()-1)) {
        noFill();
        rect((int)((mouseX-50)/183)*183+50-3, (int)((mouseY-65)/28)*28+62, 72, 17, 3);
        fill(200);
        int x = mouseX;
        int y = mouseY-125;
        if (y < 50) {
          y = mouseY;
          x = mouseX+12;
        }
        if (x > 950) {
          x = mouseX-295;
        }
        rect(x, y, 295, 125, 5);
        int totMoves = 0;
        for (int i = 0; i < 18; i++) {
          totMoves += pkmn.get((int)(mouseX-50)/183*25+(int)(mouseY-65)/28%25).cMoves[i][1];
        }
        fill(0);

        pkmn.get((int)(mouseX-50)/183*25+(int)(mouseY-65)/28%25).cMoves = sortMoves(pkmn.get((int)(mouseX-50)/183*25+(int)(mouseY-65)/28%25).cMoves);
        for (int i = 0; i < 18; i++) {
          image(images[pkmn.get((int)(mouseX-50)/183*25+(int)(mouseY-65)/28%25).cMoves[i][0]], x+5+i/6*100, y+5+i%6*20);
          textSize(12);
          text((double)((int)((double)pkmn.get((int)(mouseX-50)/183*25+(int)(mouseY-65)/28%25).cMoves[i][1]/totMoves*40000))/100+"%", x+45+i/6*100, y+5+i%6*20);
        }
      }
    }
  } else if (brackets) {
    background(200);
    teams.sort(teamcomp);
    ArrayList<Team> tourney = new ArrayList<Team>();
    for (int i = teams.size()-17; i < teams.size(); i++) {
      tourney.add(new Team(teams.get(i)));
    }
    b.drawBracket(tourney, images);
    if (tutorial) {
      textSize(20);
      textAlign(CENTER, TOP);
      text("Press SPACE to go back to Tournament View", 650, 765);
      textAlign(LEFT, TOP);
      textSize(12);
    }
    sim = false;
  } else {
    if (sim) {
      offset = 0;
      float totweight = 0;
      for (Pkmn p : pkmn) {
        totweight += p.usage();
      }

      teams = new ArrayList<Team>();

      for (int i = 0; i < part; i++) {
        ArrayList<Pkmn> newTeam = new ArrayList<Pkmn>();
        for (int j = 0; j < 6; j++) {
          double metaPick = random(totweight);
          boolean picked = false;
          boolean pickedmove = false;
          Pkmn p1 = new Pkmn(0, 0);
          for (Pkmn p : pkmn) {
            if (p.usage() > metaPick && !picked) {
              float weight = 0;
              p1 = new Pkmn(p);
              for (int k = 0; k < 18; k++) {
                weight += p.moves[k];
              }
              boolean alreadyIn = false;
              for (int k = 0; k < 4; k++) {
                do {
                  alreadyIn = false;
                  double pick = random(weight);
                  pickedmove = false;
                  for (int l = 0; l < 18; l++) {
                    if (p1.moves[l] > pick && !pickedmove) {
                      pickedmove = true;
                      p1.moveset[k] = l;

                      for (int m  = 0; m < k; m++) {

                        if (p1.moveset[k] == p1.moveset[m] && k > m) {
                          alreadyIn = true;
                        }
                      }
                    }
                    pick -= p1.moves[l];
                  }
                } while (alreadyIn);
              }
              picked = true;
            }
            metaPick -= p.usage();
          }
          newTeam.add(p1);
        }
        teams.add(new Team(newTeam.get(0), newTeam.get(1), newTeam.get(2), newTeam.get(3), newTeam.get(4), newTeam.get(5)));
      }

      for (Pkmn p : pkmn) {
        p.reset();
      }

      for (Pkmn p : pkmn) {
        p.setup = false;
      }
      cutteams = 0;
      tourney++;

      for (int i  = 0; i < games; i++) {
        teams.sort(teamcomp);
        teams = roundSim(teams);
      }
      teams.sort(teamcomp);

      for (int i = 0; i < teams.size(); i++) {
        for (Pkmn p : pkmn) {
          for (int j = 0; j < 6; j++) {
            if (p.equals(teams.get(i).pkmn[j])) {
              p.teams++;
              if (tourney != 1) {
                p.cUsage++;
              }
              p.wins += teams.get(i).score;
              p.topTeams += Math.pow((float)teams.get(i).score/games, 5); // change this 
              for (int k = 0; k < 4; k++) {
                p.moves[teams.get(i).pkmn[j].moveset[k]] += Math.pow((float)teams.get(i).score/games, 5);
                if (tourney != 1) {
                  for (int l = 0; l < 18; l++) {
                    if (p.cMoves[l][0] == teams.get(i).pkmn[j].moveset[k]) {
                      p.cMoves[l][1]++;
                    }
                  }
                }
              }
              if (teams.get(i).score >= teams.get(part-32).score) {
                p.topCut++;
                cutteams++;
              }
            }
          }
        }
      }
      mTop = 0;
      mCut = 0;
      for (Pkmn p : pkmn) {
        p.calc();
        mTop = Math.max(mTop, p.toppct);
        mCut = Math.max(mCut, p.cutpct);
      }
    }
    pkmn.sort(pkmncomp);
    sim = false;

    textSize(20);
    text("Usage", 100, 90);
    text("Top Teams", 325, 90);
    text("Meta", 1050, 90);
    text("Tournament #"+tourney, 100, 40);
    text("Press SPACE for Next Tournament", 325, 40);
    text("Hover HERE for Controls", 715, 40);
    textSize(12);

    offset = constrain(offset, 0, part-32);
    int o = constrain(offset, 0, max(0, pkmn.size()-32));

    for (int i = o; i < o+32; i++) {
      image(images[pkmn.get(i).t1], 100, 100+i*20+30-20*o);
      if (pkmn.get(i).t2 != 18) {
        image(images[pkmn.get(i).t2], 135, 100+i*20+30-20*o);
      }
      text((double)((int)(pkmn.get(i).rawUsage()*10000))/100+"%", 190, 100+i*20+30-20*o);
    }

    pkmn.sort(usagecomp);
    o = constrain(offset, 0, max(0, pkmn.size()-32));

    for (int i = o; i < o+32; i++) {
      image(images[pkmn.get(i).t1], 1050, 100+i*20+30-20*o);
      if (pkmn.get(i).t2 != 18) {
        image(images[pkmn.get(i).t2], 1085, 100+i*20+30-20*o);
      }
      text((double)((int)(pkmn.get(i).usage()*1000))/1000+"", 1140, 100+i*20+30-20*o);
    }

    o = constrain(offset, 0, max(0, part-32));

    for (int i = o; i < o+32; i++) {
      for (int j = 0; j < 6; j++) {
        image(images[teams.get(part-i-1).pkmn[j].t1], 325+100*j, 100+i*20+30-20*o);
        if (teams.get(part-i-1).pkmn[j].t2 != 18) {
          image(images[teams.get(part-i-1).pkmn[j].t2], 360+100*j, 100+i*20+30-20*o);
        }
      }
      text(teams.get(part-i-1).score+"-"+(games-teams.get(part-i-1).score), 915, 100+i*20+30-20*o);
    }

    if (mouseX == constrain(mouseX, 325, 924) && mouseY == constrain(mouseY, 130, 769) && (mouseX-325)%100 < 67 && (mouseY-130)%20 < 12) {
      stroke(0);
      noFill();
      rect(322+(int)(mouseX-325)/100*100, 127+(int)(mouseY-130)/20*20, 72, 17, 3);
      fill(200);
      rect(mouseX, mouseY-40, 91, 41, 5);
      fill(0);
      for (int i = 0; i < 4; i++) {
        image(images[teams.get(part-1-o-(int)(mouseY-130)/20).pkmn[(int)(mouseX-325)/100].moveset[i]], mouseX+5+50*(i%2), mouseY-35+20*(i/2));
      }
    } else  if (mouseX == constrain(mouseX, 780, 825) && mouseY == constrain(mouseY, 45, 60)) {
      stroke(0);
      fill(200);
      rect(mouseX-316, mouseY, 322, 102, 5);
      fill(0);

      text("ENTER to see Cumulative Stats for all Types\nB to simulate a Bracket between the Top 16 Teams\nHover over a Pokemon to see its Moveset\nUP and DOWN to browse the Tournament Standings\nSPACE to return to this screen in Stat or Bracket View", mouseX-310, mouseY + 5);
    }
  }
}

ArrayList<Team> roundSim(ArrayList<Team> t) {
  for (int i = 0; i < t.size(); i += 2) {
    double adv = 0;
    for (int j = 0; j < 6; j++) {
      adv -= t.get(i).pkmnvTeam(t.get(i+1).pkmn[j], tc);
      adv += t.get(i+1).pkmnvTeam(t.get(i).pkmn[j], tc);
    }
    if (adv > 0) {
      t.get(i).score++;
    } else {
      t.get(i+1).score++;
    }
    t.get(i).id = random(1);
    t.get(i+1).id = random(1);
  }
  return t;
}

int[][] sortMoves(int[][] moves) {
  for (int i = 0; i < moves.length; i++) {
    for (int j = 0; j < moves.length-1; j++) {
      if (moves[j][1] < moves[j+1][1]) {
        int[] proxy = moves[j];
        moves[j] = moves[j+1];
        moves[j+1] = proxy;
      }
    }
  }
  return moves;
}

void keyPressed() {
  if (key == ' ') {
    if (!stats && ! brackets) {
      if (!sim) {
        textSize(20);
        text("Simulating...", 1050, 40);
        textSize(12);
      }
      sim = true;
    }
    if (stats || brackets) {
      tutorial = false;
    }
    stats = false;
    brackets = false;
    //redraw();
  } else if (keyCode == ENTER) {
    stats = true;
    brackets = false;
    //redraw();
  } else  if (key == 'b') {
    sim = true;
    brackets = true;
    stats = false;
    //redraw();
  } else if (keyCode == UP) {
    offset--;
  } else if (keyCode == DOWN) {
    offset++;
  } else if (keyCode == 34) {
    offset += 32;
  } else if (keyCode == 33) {
    offset -= 32;
  } else if (keyCode == 36) {
    offset = 0;
  } else if (keyCode == 35) {
    offset = Integer.MAX_VALUE;
  }
}
