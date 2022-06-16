import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class typeTourneyMoves extends PApplet {

 
double[][] tc = { { 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }// nor
  , { 1, .5f, 2, 1, .5f, .5f, 1, 1, 2, 1, 1, .5f, 2, 1, 1, 1, .5f, .5f }// fir
  , { 1, .5f, .5f, 2, 2, .5f, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, .5f, 1 }// wat
  , { 1, 1, 1, .5f, 1, 1, 1, 1, 2, .5f, 1, 1, 1, 1, 1, 1, .5f, 1 }// ele
  , { 1, 2, .5f, .5f, .5f, 2, 1, 2, .5f, 2, 1, 2, 1, 1, 1, 1, 1, 1 }// gra
  , { 1, 2, 1, 1, 1, .5f, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1 }// ice
  , { 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, .5f, .5f, 1, 1, .5f, 1, 2 }// fig
  , { 1, 1, 1, 1, .5f, 1, .5f, .5f, 2, 1, 2, .5f, 1, 1, 1, 1, 1, .5f }// poi
  , { 1, 1, 2, 0, 2, 2, 1, .5f, 1, 1, 1, 1, .5f, 1, 1, 1, 1, 1 }// gro
  , { 1, 1, 1, 2, .5f, 2, .5f, 1, 0, 1, 1, .5f, 2, 1, 1, 1, 1, 1 }// fly
  , { 1, 1, 1, 1, 1, 1, .5f, 1, 1, 1, .5f, 2, 1, 2, 1, 2, 1, 1 }// psy
  , { 1, 2, 1, 1, .5f, 1, .5f, 1, .5f, 2, 1, 1, 2, 1, 1, 1, 1, 1 }// bug
  , { .5f, .5f, 2, 1, 2, 1, 2, .5f, 2, .5f, 1, 1, 1, 1, 1, 1, 2, 1 }// roc
  , { 0, 1, 1, 1, 1, 1, 0, .5f, 1, 1, 1, .5f, 1, 2, 1, 2, 1, 1 }// gho
  , { 0, .5f, .5f, .5f, .5f, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2 }// dra
  , { 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 0, 2, 1, .5f, 1, .5f, 1, 2 }// dar
  , { .5f, 2, 1, 1, .5f, .5f, 2, 0, 2, .5f, .5f, .5f, .5f, 1, .5f, 1, .5f, .5f }// ste
  , { 1, 1, 1, 1, 1, 1, .5f, 2, 1, 1, 1, .5f, 1, 1, 0, .5f, 2, 1 }// fai
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
  public int compare(Team t1, Team t2) {
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
  public int compare(Pkmn p1, Pkmn p2) {
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
  public int compare(Pkmn p1, Pkmn p2) {
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
  public int compare(Pkmn p1, Pkmn p2) {
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

public void setup() {
  
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

public void draw() {
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

public ArrayList<Team> roundSim(ArrayList<Team> t) {
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

public int[][] sortMoves(int[][] moves) {
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

public void keyPressed() {
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
class Bracket {
  Team[] bracketTeams = new Team[31];
  int[][] coords = new int[31][2];

  public Bracket() {
  }

  public void drawBracket(ArrayList<Team> t, PImage[] ps) {
    ArrayList<Team> t2 = new ArrayList<Team>();
    for (int i = 0; i < 16; i++) {
      t2.add(t.get(t.size()-1-i));
    }
    t = t2;

    for (int i = 0; i < 3; i++) {
      int l = round(pow(2, i));
      t2 = new ArrayList<Team>();
      for (int j = 0; j < 8/l; j++) {
        for (int k = 0; k < l; k++) {
          t2.add(t.get(j*l+k));
        }
        for (int k = 0; k < l; k++) {
          t2.add(t.get(t.size()-1-j*l-k));
        }
      }
      t = t2;
    }
    int id = 0;
    for (int i = 0; i < 16; i++) {
      drawTeam(t.get(i), 50+1050*((i/8)%2), 50+90*(i%8), ps, true);
      bracketTeams[id] = t.get(i);
      int[] pos = {50+1050*((i/8)%2), 50+90*(i%8)};
      coords[id] = pos;
      t.get(i).score = 0;
      t.get(i).id = (double)i/16;
      id++;
    }
    bracketSim(t);
    t.sort(teamcomp);
    for (int i = 15; i > 7; i--) {
      drawTeam(t.get(i), 250+650*(((i-8)/4)%2), 95+180*((i-8)%4), ps, false);
      bracketTeams[id] = t.get(i);
      int[] pos = {250+650*(((i-8)/4)%2), 95+180*((i-8)%4)};
      coords[id] = pos;
      fill(180);
      noStroke();
      rect(220+850*(((i-8)/4)%2), 80+180*((i-8)%4), 10, 90, 5);
      id++;
    }
    bracketSim(t);
    t.sort(teamcomp);
    for (int i = 15; i > 11; i--) {
      drawTeam(t.get(i), 400+350*(((i-12)/2)%2), 185+360*((i-12)%2), ps, false);
      bracketTeams[id] = t.get(i);
      int[] pos = {400+350*(((i-12)/2)%2), 185+360*((i-12)%2)};
      coords[id] = pos;
      fill(180);
      noStroke();
      rect(370+550*(((i-12)/2)%2), 156+360*((i-12)%2), 10, 110, 5);
      id++;
    }
    bracketSim(t);
    t.sort(teamcomp);
    drawTeam(t.get(14), 525, 275, ps, false);
    drawTeam(t.get(15), 625, 455, ps, false);
    bracketTeams[28] = t.get(14);
    int[] pos = {525, 275};
    coords[28] = pos;
    bracketTeams[29] = t.get(15);
    int[] pos2 = {625, 455};
    coords[29] = pos2;
    fill(180);
    noStroke();
    rect(495, 246, 10, 290, 5);
    rect(795, 246, 10, 290, 5);
    rect(645, 336, 10, 108, 5);



    bracketSim(t);
    t.sort(teamcomp);
    drawTeam(t.get(15), 575, 365, ps, false);
    bracketTeams[30] = t.get(15);
    int[] pos3 = {575, 365};
    coords[30] = pos3;

    for (int i = 0; i < coords.length; i++) {
      if (mouseX == constrain(mouseX, coords[i][0], coords[i][0]+155) && mouseY == constrain(mouseY, coords[i][1], coords[i][1]+55)) {
        if ((mouseX - coords[i][0])%85 == constrain((mouseX - coords[i][0])%85, 0, 67) && (mouseY - coords[i][1])%20 == constrain((mouseY - coords[i][1])%20, 0, 12)) {
          noFill();
          rect((int)(mouseX-coords[i][0])/85*85+coords[i][0]-3, (int)(mouseY-coords[i][1])/20*20+coords[i][1]-3, 72, 17, 3);
          fill(200);
          rect(mouseX, mouseY-40, 91, 41, 5);
          for (int j = 0; j < 4; j++) {
            image(images[bracketTeams[i].pkmn[(int)(mouseX-coords[i][0])/85+(int)(mouseY-coords[i][1])/20*2].moveset[j]], mouseX+5+50*(j%2), mouseY-35+20*(j/2));
          }
          fill(0);
        }
      }
    }

    textSize(16);
    textAlign(CENTER, TOP);
    text("Hover over a Pokemon to see its Moveset", 650, 25);
    textAlign(LEFT, TOP);
  }

  private void drawTeam(Team t, int x, int y, PImage[] ps, boolean s) {
    fill(200);
    stroke(0);
    rect(x-5, y-5, 161, 61, 5);

    for (int j = 0; j < 6; j++) {
      image(ps[t.pkmn[j].t1], x+j%2*85, y+j/2*20);
      if (t.pkmn[j].t2 != 18) {
        image(ps[t.pkmn[j].t2], x+j%2*85+35, y+j/2*20);
      }
      fill(0);

      if (s) {
        text(PApplet.parseInt(t.score)+"-"+(games-PApplet.parseInt(t.score)), x+65, y+57);
      }
    }
  }

  public ArrayList<Team> bracketSim(ArrayList<Team> t) {
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
    }
    return t;
  }
}
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
      moves[i] = 0.01f;
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
      moves[i] = 0.01f;
    }
  }

  public double usage() {
    if (setup) { 
      return 1;
    }
    return Math.pow(Math.max(Math.log((double)teams/part*1000+1)*(Math.pow(toppct/mTop, 2)*4+Math.pow(cutpct/mCut, 2)), 0.1f),1);
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
Comparator<Double> dmgComp = new Comparator<Double>() {
  public int compare(Double d1, Double d2) {
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

  public boolean larger(Team t) { 
    if (getVal() > t.getVal()) {
      return true;
    }
    return false;
  }

  public double pkmnvTeam(Pkmn p, double[][] tc) { 
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
          mPow *= 1.5f;
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

  public double getVal() {
    return id+score;
  }

  public String toString() {
    String s = "";
    for (int i = 0; i < 6; i++) {
      s+= pkmn[i].t1 +" "+pkmn[i].t2+" ";
    }

    return s;
  }
}
  public void settings() {  size(1300, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "typeTourneyMoves" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
