class Bracket {
  Team[] bracketTeams = new Team[31];
  int[][] coords = new int[31][2];

  public Bracket() {
  }

  void drawBracket(ArrayList<Team> t, PImage[] ps) {
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
        text(int(t.score)+"-"+(games-int(t.score)), x+65, y+57);
      }
    }
  }

  ArrayList<Team> bracketSim(ArrayList<Team> t) {
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
