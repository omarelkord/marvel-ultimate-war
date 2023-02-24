package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Disarm;
import model.effects.Dodge;
import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;
import model.effects.PowerUp;
import model.effects.Root;
import model.effects.Shield;
import model.effects.Shock;
import model.effects.Silence;
import model.effects.SpeedUp;
import model.effects.Stun;
import model.world.*;

public class Game {
	private static ArrayList<Champion> availableChampions = new ArrayList<>();
	private static ArrayList<Ability> availableAbilities = new ArrayList<>();
	private Player firstPlayer;
	private Player secondPlayer;
	private Object[][] board;
	private PriorityQueue turnOrder;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private final static int BOARDWIDTH = 5;
	private final static int BOARDHEIGHT = 5;

	public Game(Player first, Player second) {
		firstPlayer = first;
		secondPlayer = second;
		board = new Object[BOARDWIDTH][BOARDHEIGHT];
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
		prepareChampionTurns();
	}

	public static void loadAbilities(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Ability a = null;
			AreaOfEffect ar = null;
			switch (content[5]) {
			case "SINGLETARGET":
				ar = AreaOfEffect.SINGLETARGET;
				break;
			case "TEAMTARGET":
				ar = AreaOfEffect.TEAMTARGET;
				break;
			case "SURROUND":
				ar = AreaOfEffect.SURROUND;
				break;
			case "DIRECTIONAL":
				ar = AreaOfEffect.DIRECTIONAL;
				break;
			case "SELFTARGET":
				ar = AreaOfEffect.SELFTARGET;
				break;

			}
			Effect e = null;
			if (content[0].equals("CC")) {
				switch (content[7]) {
				case "Disarm":
					e = new Disarm(Integer.parseInt(content[8]));
					break;
				case "Dodge":
					e = new Dodge(Integer.parseInt(content[8]));
					break;
				case "Embrace":
					e = new Embrace(Integer.parseInt(content[8]));
					break;
				case "PowerUp":
					e = new PowerUp(Integer.parseInt(content[8]));
					break;
				case "Root":
					e = new Root(Integer.parseInt(content[8]));
					break;
				case "Shield":
					e = new Shield(Integer.parseInt(content[8]));
					break;
				case "Shock":
					e = new Shock(Integer.parseInt(content[8]));
					break;
				case "Silence":
					e = new Silence(Integer.parseInt(content[8]));
					break;
				case "SpeedUp":
					e = new SpeedUp(Integer.parseInt(content[8]));
					break;
				case "Stun":
					e = new Stun(Integer.parseInt(content[8]));
					break;
				}
			}
			switch (content[0]) {
			case "CC":
				a = new CrowdControlAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), e);
				break;
			case "DMG":
				a = new DamagingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			case "HEL":
				a = new HealingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			}
			availableAbilities.add(a);
			line = br.readLine();
		}
		br.close();
	}

	public static void loadChampions(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Champion c = null;
			switch (content[0]) {
			case "A":
				c = new AntiHero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;

			case "H":
				c = new Hero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			case "V":
				c = new Villain(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			}

			c.getAbilities().add(findAbilityByName(content[8]));
			c.getAbilities().add(findAbilityByName(content[9]));
			c.getAbilities().add(findAbilityByName(content[10]));
			availableChampions.add(c);
			line = br.readLine();
		}
		br.close();
	}

	private static Ability findAbilityByName(String name) {
		for (Ability a : availableAbilities) {
			if (a.getName().equals(name))
				return a;
		}
		return null;
	}

	public void placeCovers() {
		int i = 0;
		while (i < 5) {
			int x = ((int) (Math.random() * (BOARDWIDTH - 2))) + 1;
			int y = (int) (Math.random() * BOARDHEIGHT);

			if (board[x][y] == null) {
				board[x][y] = new Cover(x, y);
				i++;
			}
		}

	}

	public void placeChampions() {
		int i = 1;
		for (Champion c : firstPlayer.getTeam()) {
			board[0][i] = c;
			c.setLocation(new Point(0, i));
			i++;
		}
		i = 1;
		for (Champion c : secondPlayer.getTeam()) {
			board[BOARDHEIGHT - 1][i] = c;
			c.setLocation(new Point(BOARDHEIGHT - 1, i));
			i++;
		}

	}

	public Champion getCurrentChampion() {
		return (Champion) turnOrder.peekMin();
	}

	public Player checkGameOver() {
		if (firstPlayer.getTeam().size() == 0)
			return secondPlayer;
		else if (secondPlayer.getTeam().size() == 0)
			return firstPlayer;
		else
			return null;
	}

	public void move(Direction d) throws NotEnoughResourcesException, UnallowedMovementException {
		if (hasEffect(getCurrentChampion(), "Root"))
			throw new UnallowedMovementException("You can not move while being rooted");
		if (getCurrentChampion().getCurrentActionPoints() < 1)
			throw new NotEnoughResourcesException("You need at least one action point to move");
		int currx = (int) getCurrentChampion().getLocation().getX();
		int curry = (int) getCurrentChampion().getLocation().getY();
		int newx = currx;
		int newy = curry;
		if (d == Direction.UP)
			newx = newx + 1;
		else if (d == Direction.DOWN)
			newx = newx - 1;
		else if (d == Direction.LEFT)
			newy = newy - 1;
		else if (d == Direction.RIGHT)
			newy = newy + 1;
		if (newx < 0 || newx >= BOARDHEIGHT || newy < 0 || newy >= BOARDWIDTH)
			throw new UnallowedMovementException("Can not move out of the board");
		if (board[newx][newy] == null) {
			board[currx][curry] = null;
			board[newx][newy] = getCurrentChampion();
			getCurrentChampion().setLocation(new Point(newx, newy));
			getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints() - 1);
		} else
			throw new UnallowedMovementException("Target cell is not empty");

	}

	private boolean hasEffect(Champion currentChampion, String s) {
		for (Effect e : currentChampion.getAppliedEffects()) {
			if (e.getName().equals(s))
				return true;
		}
		return false;
	}

	public void attack2(Direction dir) throws NotEnoughResourcesException, ChampionDisarmedException {

		Champion c = this.getCurrentChampion();

		for (Effect e : c.getAppliedEffects()) {
			if (e instanceof Disarm)
				throw new ChampionDisarmedException("Champion is disarmed!");
		}

		if (c.getCurrentActionPoints() < 2)
			throw new NotEnoughResourcesException();

		c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);

		Point pointChampion = c.getLocation();
		ArrayList<Damageable> directionTargets = targetsInDirection(pointChampion, dir, c.getAttackRange());
		ArrayList<Damageable> oppositeTeam = oppositeTeam(c);

		Damageable target = null;
		int dodge = 0;

		// GET FIRST VALID TARGET IN DIRECTION WITHIN RANGE
		for (Damageable d : directionTargets) {

			if (d instanceof Cover || (d instanceof Champion && oppositeTeam.contains(d))) {
				target = d;
				break;
			}
		}

		if (target != null) {
			if (target instanceof Champion) {
				for (Effect e : ((Champion) target).getAppliedEffects()) {

					if (e instanceof Shield) {
						((Champion) target).getAppliedEffects().remove(e);
						e.remove((Champion) target);
						return;
					}

					if (e instanceof Dodge)
						dodge = (int) (Math.random() * 2);
				}

				if (dodge == 0) {
					specialInteraction(c, (Champion) target);
				}
			}

			else if (target instanceof Cover)
				target.setCurrentHP(target.getCurrentHP() - c.getAttackDamage());

			if (target.getCurrentHP() == 0)
				removeDead(target);

		}
	}


	public void attack(Direction d)
			throws NotEnoughResourcesException, ChampionDisarmedException {
		if (hasEffect(getCurrentChampion(), "Disarm"))
			throw new ChampionDisarmedException("Can not attack while being disarmed");
		if (getCurrentChampion().getCurrentActionPoints() < 2)
			throw new NotEnoughResourcesException("You need at least two action points \n to perform a normal attack");
		int currx = (int) getCurrentChampion().getLocation().getX();
		int curry = (int) getCurrentChampion().getLocation().getY();
		for (int i = 0; i < getCurrentChampion().getAttackRange(); i++) {
			if (d == Direction.UP)
				currx++;
			else if (d == Direction.DOWN)
				currx--;
			else if (d == Direction.LEFT)
				curry--;
			else if (d == Direction.RIGHT)
				curry++;
			if (currx < 0 || currx >= BOARDHEIGHT || curry < 0 || curry >= BOARDWIDTH)
				return;
			else if (board[currx][curry] != null) {
				if (board[currx][curry] instanceof Cover) {
					int curhp = ((Cover) board[currx][curry]).getCurrentHP();
					curhp -= getCurrentChampion().getAttackDamage();
					((Cover) board[currx][curry]).setCurrentHP(curhp);
					if (curhp <= 0)
						board[currx][curry] = null;
					return;
				} else if (board[currx][curry] instanceof Champion) {

					int damage = getCurrentChampion().getAttackDamage();
					Champion target = (Champion) board[currx][curry];
					if (firstPlayer.getTeam().contains(getCurrentChampion()) && firstPlayer.getTeam().contains(target))
						continue;
					else if (secondPlayer.getTeam().contains(getCurrentChampion())
							&& secondPlayer.getTeam().contains(target))
						continue;

					Champion curr = getCurrentChampion();
					 if (hasEffect(target, "Dodge")) {
						int r = ((int) (Math.random() * 100)) + 1;
						if (r <= 50) {
							curr.setCurrentActionPoints(curr.getCurrentActionPoints() - 2);
							return;
						}
					}

					if (hasEffect(target, "Shield")) {
						for (Effect e : target.getAppliedEffects()) {
							if (e.getName().equals("Shield")) {
								e.remove(target);
								target.getAppliedEffects().remove(e);
								curr.setCurrentActionPoints(curr.getCurrentActionPoints() - 2);
								return;
							}
						}
					}
					
					else if ((curr instanceof Hero && !(target instanceof Hero))
							|| (curr instanceof Villain && !(target instanceof Villain))
							|| (curr instanceof AntiHero && !(target instanceof AntiHero)))
						damage = (int) (damage * 1.5);

					target.setCurrentHP(target.getCurrentHP() - damage);
					curr.setCurrentActionPoints(curr.getCurrentActionPoints() - 2);
					ArrayList<Damageable> targets = new ArrayList<Damageable>();
					targets.add(target);
					cleanup(targets);
					return;

				}

			}

		}
	}


	public ArrayList<Damageable> getTargetOfAttack(Direction d){

		ArrayList<Damageable> finalTarget = new ArrayList<>();

		int currx = (int) getCurrentChampion().getLocation().getX();
		int curry = (int) getCurrentChampion().getLocation().getY();

		for (int i = 0; i < getCurrentChampion().getAttackRange(); i++) {
			if (d == Direction.UP)
				currx++;
			else if (d == Direction.DOWN)
				currx--;
			else if (d == Direction.LEFT)
				curry--;
			else if (d == Direction.RIGHT)
				curry++;

			if (currx < 0 || currx >= BOARDHEIGHT || curry < 0 || curry >= BOARDWIDTH) {
				return null;
			}

			else if (board[currx][curry] != null) {

				if (board[currx][curry] instanceof Cover) {

					finalTarget.add((Damageable) board[currx][curry]);
					return finalTarget;

				} else if (board[currx][curry] instanceof Champion) {

					Champion target = (Champion) board[currx][curry];

					if (firstPlayer.getTeam().contains(getCurrentChampion()) && firstPlayer.getTeam().contains(target))
						continue;
					else if (secondPlayer.getTeam().contains(getCurrentChampion())
							&& secondPlayer.getTeam().contains(target))
						continue;


					Champion curr = getCurrentChampion();

					if (hasEffect(target, "Dodge")) {
						int r = ((int) (Math.random() * 100)) + 1;
						if (r <= 50) {
							return finalTarget;
						}
					}

					if (hasEffect(target, "Shield")) {
						for (Effect e : target.getAppliedEffects()) {
							if (e.getName().equals("Shield")) {
								return finalTarget;
							}
						}
					}


					finalTarget.add(target);
					return finalTarget;

				}
			}
		}

		return finalTarget;
	}

	public void castAbility(Ability a)
			throws NotEnoughResourcesException, AbilityUseException, CloneNotSupportedException {
		validateCastAbility(a);
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		if (a.getCastArea() == AreaOfEffect.SELFTARGET) {
			targets.add(getCurrentChampion());
		} else if (a.getCastArea() == AreaOfEffect.TEAMTARGET) {
			ArrayList<Champion> team = null;
			if (a instanceof DamagingAbility || (a instanceof CrowdControlAbility
					&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.DEBUFF)) {
				if (firstPlayer.getTeam().contains(getCurrentChampion()))
					team = secondPlayer.getTeam();
				else
					team = firstPlayer.getTeam();
			} else if (a instanceof HealingAbility
					|| (a instanceof CrowdControlAbility && (a instanceof CrowdControlAbility
							&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF))) {
				if (firstPlayer.getTeam().contains(getCurrentChampion()))
					team = firstPlayer.getTeam();
				else
					team = secondPlayer.getTeam();
			}
			for (Champion c : team) {
				int x = (int) c.getLocation().getX();
				int y = (int) c.getLocation().getY();
				int distance = Math.abs((int) getCurrentChampion().getLocation().getX() - x)
						+ Math.abs((int) getCurrentChampion().getLocation().getY() - y);
				if (distance <= a.getCastRange())
					targets.add(c);
			}
		} else if (a.getCastArea() == AreaOfEffect.SURROUND) {
			ArrayList<Point> possiblePoints = new ArrayList<Point>();
			int currx = (int) getCurrentChampion().getLocation().getX();
			int curry = (int) getCurrentChampion().getLocation().getY();
			possiblePoints.add(new Point(currx + 1, curry));
			possiblePoints.add(new Point(currx - 1, curry));
			possiblePoints.add(new Point(currx, curry + 1));
			possiblePoints.add(new Point(currx, curry - 1));
			possiblePoints.add(new Point(currx + 1, curry - 1));
			possiblePoints.add(new Point(currx + 1, curry + 1));
			possiblePoints.add(new Point(currx - 1, curry - 1));
			possiblePoints.add(new Point(currx - 1, curry + 1));
			targets = prepareTargetsFromPoints(a, possiblePoints);
		}

		a.execute(targets);
		getCurrentChampion().setMana(getCurrentChampion().getMana() - a.getManaCost());
		getCurrentChampion()
				.setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints() - a.getRequiredActionPoints());
		cleanup(targets);
		a.setCurrentCooldown(a.getBaseCooldown());

	}

	public ArrayList<Damageable> getTargetsOfCast(Ability a)		{
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		if (a.getCastArea() == AreaOfEffect.SELFTARGET) {
			targets.add(getCurrentChampion());
		} else if (a.getCastArea() == AreaOfEffect.TEAMTARGET) {
			ArrayList<Champion> team = null;
			if (a instanceof DamagingAbility || (a instanceof CrowdControlAbility
					&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.DEBUFF)) {
				if (firstPlayer.getTeam().contains(getCurrentChampion()))
					team = secondPlayer.getTeam();
				else
					team = firstPlayer.getTeam();
			} else if (a instanceof HealingAbility
					|| (a instanceof CrowdControlAbility && (a instanceof CrowdControlAbility
					&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF))) {
				if (firstPlayer.getTeam().contains(getCurrentChampion()))
					team = firstPlayer.getTeam();
				else
					team = secondPlayer.getTeam();
			}
			for (Champion c : team) {
				int x = (int) c.getLocation().getX();
				int y = (int) c.getLocation().getY();
				int distance = Math.abs((int) getCurrentChampion().getLocation().getX() - x)
						+ Math.abs((int) getCurrentChampion().getLocation().getY() - y);
				if (distance <= a.getCastRange())
					targets.add(c);
			}
		} else if (a.getCastArea() == AreaOfEffect.SURROUND) {
			ArrayList<Point> possiblePoints = new ArrayList<Point>();
			int currx = (int) getCurrentChampion().getLocation().getX();
			int curry = (int) getCurrentChampion().getLocation().getY();
			possiblePoints.add(new Point(currx + 1, curry));
			possiblePoints.add(new Point(currx - 1, curry));
			possiblePoints.add(new Point(currx, curry + 1));
			possiblePoints.add(new Point(currx, curry - 1));
			possiblePoints.add(new Point(currx + 1, curry - 1));
			possiblePoints.add(new Point(currx + 1, curry + 1));
			possiblePoints.add(new Point(currx - 1, curry - 1));
			possiblePoints.add(new Point(currx - 1, curry + 1));
			targets = prepareTargetsFromPoints(a, possiblePoints);
		}

		return targets;
	}

	public ArrayList<Damageable> getTargetsOfCastSin(Ability a, int x, int y){
		ArrayList<Damageable> targets = new ArrayList<>();
		targets.add((Damageable) this.getBoard()[x][y]);

		return targets;
	}

	private void validateCastAbility(Ability a) throws NotEnoughResourcesException, AbilityUseException {
		if (getCurrentChampion().getMana() < a.getManaCost())
			throw new NotEnoughResourcesException(
					"you need at least " + a.getManaCost() + " mana to cast this ability");
		else if (getCurrentChampion().getCurrentActionPoints() < a.getRequiredActionPoints())
			throw new NotEnoughResourcesException(
					"you need at least " + a.getRequiredActionPoints() + " action points \n to cast this ability");
		else if (hasEffect(getCurrentChampion(), "Silence"))
			throw new AbilityUseException("You can not cast an ability while being silenced");
		else if (a.getCurrentCooldown() > 0)
			throw new AbilityUseException("You can not use an ability while it is in cooldown");
	}
	public ArrayList<Damageable> getTargetsOfCastDir(Ability a, Direction d){

		ArrayList<Point> possiblePoints = new ArrayList<Point>();
		int currx = (int) getCurrentChampion().getLocation().getX();
		int curry = (int) getCurrentChampion().getLocation().getY();
		for (int i = 0; i < a.getCastRange(); i++) {
			if (d == Direction.UP) {
				currx++;
				if (currx == BOARDHEIGHT)
					break;
			} else if (d == Direction.DOWN) {
				currx--;
				if (currx < 0)
					break;
			} else if (d == Direction.LEFT) {
				curry--;
				if (curry < 0)
					break;
			} else if (d == Direction.RIGHT) {
				curry++;
				if (curry == BOARDWIDTH)
					break;
			}
			possiblePoints.add(new Point(currx, curry));
		}

		ArrayList<Damageable> targets = prepareTargetsFromPoints(a, possiblePoints);

		return targets;
	}

	public void castAbility(Ability a, Direction d)
			throws NotEnoughResourcesException, AbilityUseException, CloneNotSupportedException {
		validateCastAbility(a);

		ArrayList<Point> possiblePoints = new ArrayList<Point>();
		int currx = (int) getCurrentChampion().getLocation().getX();
		int curry = (int) getCurrentChampion().getLocation().getY();
		for (int i = 0; i < a.getCastRange(); i++) {
			if (d == Direction.UP) {
				currx++;
				if (currx == BOARDHEIGHT)
					break;
			} else if (d == Direction.DOWN) {
				currx--;
				if (currx < 0)
					break;
			} else if (d == Direction.LEFT) {
				curry--;
				if (curry < 0)
					break;
			} else if (d == Direction.RIGHT) {
				curry++;
				if (curry == BOARDWIDTH)
					break;
			}
			possiblePoints.add(new Point(currx, curry));
		}

		ArrayList<Damageable> targets = prepareTargetsFromPoints(a, possiblePoints);

		a.execute(targets);
		getCurrentChampion().setMana(getCurrentChampion().getMana() - a.getManaCost());
		getCurrentChampion()
				.setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints() - a.getRequiredActionPoints());

		a.setCurrentCooldown(a.getBaseCooldown());
		cleanup(targets);

	}

	private ArrayList<Damageable> prepareTargetsFromPoints(Ability a, ArrayList<Point> possiblePoints) {
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		for (Point p : possiblePoints) {
			int x = (int) p.getX();
			int y = (int) p.getY();
			if (x >= 0 && x < BOARDHEIGHT && y >= 0 && y < BOARDWIDTH) {
				Object o = board[x][y];
				if (o != null) {
					if (o instanceof Cover) {
						if (a instanceof DamagingAbility)
							targets.add((Damageable) o);
					} else {

						boolean friendly = (firstPlayer.getTeam().contains(getCurrentChampion())
								&& firstPlayer.getTeam().contains(o))
								|| ((secondPlayer.getTeam().contains(getCurrentChampion())
								&& secondPlayer.getTeam().contains(o)));
						if (a instanceof HealingAbility && friendly)
							targets.add((Damageable) o);
						else if (a instanceof DamagingAbility && !friendly) {
							Champion c = (Champion) o;
							if (hasEffect(c, "Shield")) {
								for (Effect e : c.getAppliedEffects()) {
									if (e instanceof Shield) {
										c.getAppliedEffects().remove(e);
										break;
									}
								}
							} else
								targets.add((Damageable) o);
						} else if (a instanceof CrowdControlAbility
								&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.DEBUFF && !friendly)
							targets.add((Damageable) o);
						else if (a instanceof CrowdControlAbility
								&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF && friendly)
							targets.add((Damageable) o);
					}
				}
			}
		}
		return targets;
	}

	public void castAbility(Ability a, int x, int y) throws NotEnoughResourcesException, AbilityUseException,
			InvalidTargetException, CloneNotSupportedException {
		validateCastAbility(a);
		if (board[x][y] == null)
			throw new InvalidTargetException("You can not cast an ability on an empty cell");
		int distance = Math.abs((int) getCurrentChampion().getLocation().getX() - x)
				+ Math.abs((int) getCurrentChampion().getLocation().getY() - y);
		if (distance > a.getCastRange())
			throw new AbilityUseException("Target out of the ability's cast range");

		if (board[x][y] instanceof Cover && !(a instanceof DamagingAbility))
			throw new InvalidTargetException("Covers can only be damaged");
		if (board[x][y] instanceof Champion) {
			Champion target = (Champion) board[x][y];
			boolean friendly = ((firstPlayer.getTeam().contains(getCurrentChampion())
					&& firstPlayer.getTeam().contains(target))
					|| ((secondPlayer.getTeam().contains(getCurrentChampion())
							&& secondPlayer.getTeam().contains(target)))) ? true : false;
			if (friendly && a instanceof DamagingAbility)
				throw new InvalidTargetException("Can not cast damaging ability on friendly targets");
			if (friendly && a instanceof CrowdControlAbility
					&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.DEBUFF)
				throw new InvalidTargetException("Can not debuff friendly targets");
			if (!friendly && a instanceof HealingAbility)
				throw new InvalidTargetException("Can not cast healing ability on enemy targets");
			if (!friendly && a instanceof CrowdControlAbility
					&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF)
				throw new InvalidTargetException("Can not buff enemy targets");
		}
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		if (board[x][y] instanceof Cover && a instanceof DamagingAbility)
			targets.add((Cover) board[x][y]);
		else {
			Champion c = (Champion) board[x][y];
			if (hasEffect(c, "Shield")) {
				for (Effect e : c.getAppliedEffects()) {
					if (e instanceof Shield) {
						c.getAppliedEffects().remove(e);
						break;
					}
				}
			} else
				targets.add(c);
		}
		a.execute(targets);
		getCurrentChampion().setMana(getCurrentChampion().getMana() - a.getManaCost());
		getCurrentChampion()
				.setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints() - a.getRequiredActionPoints());
		a.setCurrentCooldown(a.getBaseCooldown());
		cleanup(targets);
	}

	public ArrayList<Damageable> getTargetsOfLeader(){
		ArrayList<Damageable> targets = new ArrayList<>();

		boolean unallowed = (getCurrentChampion() != firstPlayer.getLeader() && getCurrentChampion() != secondPlayer.getLeader())
				|| (getCurrentChampion() == firstPlayer.getLeader() && firstLeaderAbilityUsed)
				|| (getCurrentChampion() == secondPlayer.getLeader() && secondLeaderAbilityUsed);

		if (unallowed)
			return targets;


		if (getCurrentChampion() instanceof Hero) {
			ArrayList<Champion> team = getCurrentChampion() == firstPlayer.getLeader() ? firstPlayer.getTeam()
					: secondPlayer.getTeam();
			targets.add(getCurrentChampion());
			targets.addAll(team);

		} else if (getCurrentChampion() instanceof AntiHero) {
			for (Champion c : firstPlayer.getTeam()) {
				if (c != firstPlayer.getLeader())
					targets.add(c);
			}
			for (Champion c : secondPlayer.getTeam()) {
				if (c != secondPlayer.getLeader())
					targets.add(c);
			}
		} else if (getCurrentChampion() instanceof Villain) {
			ArrayList<Champion> enemies = getCurrentChampion() == firstPlayer.getLeader() ? secondPlayer.getTeam()
					: firstPlayer.getTeam();
			for (Champion c : enemies) {
				if (c.getCurrentHP() < (0.3 * c.getMaxHP()))
					targets.add(c);
			}
		}

		return targets;
	}


	public void useLeaderAbility() throws LeaderNotCurrentException, LeaderAbilityAlreadyUsedException {
		if (getCurrentChampion() != firstPlayer.getLeader() && getCurrentChampion() != secondPlayer.getLeader())
			throw new LeaderNotCurrentException("The current champion is not a leader");
		if (getCurrentChampion() == firstPlayer.getLeader() && firstLeaderAbilityUsed)
			throw new LeaderAbilityAlreadyUsedException("This leader already used his ability");
		if (getCurrentChampion() == secondPlayer.getLeader() && secondLeaderAbilityUsed)
			throw new LeaderAbilityAlreadyUsedException("This leader already used his ability");


		ArrayList<Champion> targets = new ArrayList<Champion>();
		if (getCurrentChampion() instanceof Hero) {
			ArrayList<Champion> team = getCurrentChampion() == firstPlayer.getLeader() ? firstPlayer.getTeam()
					: secondPlayer.getTeam();
			targets.add(getCurrentChampion());
			targets.addAll(team);

		} else if (getCurrentChampion() instanceof AntiHero) {
			for (Champion c : firstPlayer.getTeam()) {
				if (c != firstPlayer.getLeader())
					targets.add(c);
			}
			for (Champion c : secondPlayer.getTeam()) {
				if (c != secondPlayer.getLeader())
					targets.add(c);
			}
		} else if (getCurrentChampion() instanceof Villain) {
			ArrayList<Champion> enemies = getCurrentChampion() == firstPlayer.getLeader() ? secondPlayer.getTeam()
					: firstPlayer.getTeam();
			for (Champion c : enemies) {
				if (c.getCurrentHP() < (0.3 * c.getMaxHP()))
					targets.add(c);
			}
		}
		getCurrentChampion().useLeaderAbility(targets);
		if (getCurrentChampion() == firstPlayer.getLeader())
			firstLeaderAbilityUsed = true;
		else if (getCurrentChampion() == secondPlayer.getLeader())
			secondLeaderAbilityUsed = true;
	}

	private void cleanup(ArrayList<Damageable> targets) {
		for (Damageable c : targets) {
			if (c.getCurrentHP() == 0) {
				board[(int) c.getLocation().getX()][(int) c.getLocation().getY()] = null;
				firstPlayer.getTeam().remove(c);
				secondPlayer.getTeam().remove(c);
				ArrayList<Champion> temp = new ArrayList<Champion>();
				while (!turnOrder.isEmpty()) {
					if (turnOrder.peekMin() == c) {
						turnOrder.remove();
						break;
					} else
						temp.add((Champion) turnOrder.remove());
				}
				while (!temp.isEmpty())
					turnOrder.insert(temp.remove(0));

			}
		}

	}

	public void endTurn() {
		turnOrder.remove();
		if (turnOrder.isEmpty())
			prepareChampionTurns();
		while (!turnOrder.isEmpty() && hasEffect((Champion) turnOrder.peekMin(), "Stun")) {
			Champion current = (Champion) turnOrder.peekMin();
			updateTimers(current);
			turnOrder.remove();
		}
		Champion current = (Champion) turnOrder.peekMin();
		updateTimers(current);
		current.setCurrentActionPoints(current.getMaxActionPointsPerTurn());
	}

	private void updateTimers(Champion current) {
		int i = 0;
		while (i < current.getAppliedEffects().size()) {
			Effect e = current.getAppliedEffects().get(i);
			e.setDuration(e.getDuration() - 1);
			if (e.getDuration() == 0) {
				current.getAppliedEffects().remove(e);
				e.remove(current);

			} else
				i++;
		}
		for (Ability a : current.getAbilities()) {
			if (a.getCurrentCooldown() > 0)
				a.setCurrentCooldown(a.getCurrentCooldown() - 1);
		}
	}

	private void prepareChampionTurns() {
		for (Champion c : firstPlayer.getTeam())
			turnOrder.insert(c);
		for (Champion c : secondPlayer.getTeam())
			turnOrder.insert(c);
	}


	public ArrayList<Damageable> getTargetsSingle (Ability a){

		ArrayList<Damageable> res = new ArrayList<Damageable>();
		ArrayList<Point> points = new ArrayList<Point>();

		Point currPoint = this.getCurrentChampion().getLocation();

		for(int i = 0; i < board.length ; i++)
			for(int j = 0; j< board[i].length;j++){
				Point location = new Point(i,j);
				if(distance(currPoint,location)<= a.getCastRange())
					points.add(location);
			}
		res = prepareTargetsFromPoints(a,points);
		return res;
	}


	public static int distance(Point p1, Point p2) {
		return Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y);
	}


	public ArrayList<Damageable> getTargetsDirectional(Ability a){
		ArrayList<Point> possiblePoints = new ArrayList<Point>();
		int currx = (int) getCurrentChampion().getLocation().getX();
		int curry = (int) getCurrentChampion().getLocation().getY();

		getAllPoints(possiblePoints,currx,curry,Direction.UP,a.getCastRange());
		getAllPoints(possiblePoints,currx,curry,Direction.DOWN,a.getCastRange());
		getAllPoints(possiblePoints,currx,curry,Direction.LEFT,a.getCastRange());
		getAllPoints(possiblePoints,currx,curry,Direction.RIGHT,a.getCastRange());

		ArrayList<Damageable> targets = prepareTargetsFromPoints(a, possiblePoints);

		return targets;
	}

	private static void getAllPoints(ArrayList<Point> possiblePoints, int currx, int curry, Direction d, int castRange) {
		for (int i = 0; i < castRange; i++) {
			if (d == Direction.UP) {
				currx++;
				if (currx == getBoardheight())
					break;
			} else if (d == Direction.DOWN) {
				currx--;
				if (currx < 0)
					break;
			} else if (d == Direction.LEFT) {
				curry--;
				if (curry < 0)
					break;
			} else if (d == Direction.RIGHT) {
				curry++;
				if (curry == getBoardwidth())
					break;
			}
			possiblePoints.add(new Point(currx, curry));
		}
	}

	public ArrayList<Damageable> getPotentialTargets(Ability a){

		if(a == null)
			return this.getPotentialAttackTargets();
		else if(a.getCastArea() == AreaOfEffect.SINGLETARGET)
			return this.getTargetsSingle(a);
		else if(a.getCastArea() == AreaOfEffect.DIRECTIONAL)
			return this.getTargetsDirectional(a);
		else
			return this.getTargetsOfCast(a);

	}

	public Damageable getAttackTargetInDirection(Direction d){

		int currx = (int) getCurrentChampion().getLocation().getX();
		int curry = (int) getCurrentChampion().getLocation().getY();

		int range = getCurrentChampion().getAttackRange();

		for(int i=0;i<range;i++){

			if (d == Direction.UP)
				currx++;
			else if (d == Direction.DOWN)
				currx--;
			else if (d == Direction.LEFT)
				curry--;
			else if (d == Direction.RIGHT)
				curry++;
			if (currx < 0 || currx >= BOARDHEIGHT || curry < 0 || curry >= BOARDWIDTH)
				break;


			Object potential = board[currx][curry];
			if(potential instanceof Cover) {
				return (Damageable) potential;
			}
			if(potential instanceof  Champion)
				if(isEnemy((Champion) potential)) {
					return (Damageable) potential;
				}
		}

		return null;
	}


	public ArrayList<Damageable> getPotentialAttackTargets(){

		ArrayList<Damageable> targets = new ArrayList<>();
		ArrayList<String> targetNames = new ArrayList<>();


		targets.add(getAttackTargetInDirection(Direction.UP));
		targets.add(getAttackTargetInDirection(Direction.DOWN));
		targets.add(getAttackTargetInDirection(Direction.LEFT));
		targets.add(getAttackTargetInDirection(Direction.RIGHT));


		targets.removeIf(Objects::isNull);

		return targets;
	}

	public boolean isEnemy(Champion c){
		return firstPlayer.getTeam().contains(getCurrentChampion()) && secondPlayer.getTeam().contains(c) ||
				secondPlayer.getTeam().contains(getCurrentChampion()) && firstPlayer.getTeam().contains(c);
	}

	public ArrayList<Damageable> oppositeTeam(Champion c) {

		Player p1 = this.firstPlayer;
		Player p2 = this.secondPlayer;
		ArrayList<Damageable> result = new ArrayList<Damageable>();
		if (p1.getTeam().contains(c))
			result.addAll(p2.getTeam());
		else
			result.addAll(p1.getTeam());

		return result;
	}

	public static Point modifiedpoint(Point p, Direction dir) {
		if (dir == Direction.UP)
			p.x++;
		else if (dir == Direction.DOWN)
			p.x--;
		else if (dir == Direction.RIGHT)
			p.y++;
		else
			p.y--;
		return p;
	}

	public ArrayList<Damageable> targetsInDirection(Point p, Direction dir, int range) {
		ArrayList<Damageable> targetsInDirection = new ArrayList<Damageable>();

		Point cloned = new Point(p.x, p.y);
		Point point = modifiedpoint(cloned, dir);

		int i = 0;

		while (point.x >= 0 && point.x < BOARDWIDTH && point.y >= 0 && point.y < BOARDHEIGHT && i < range) {

			Damageable d = (Damageable) this.board[point.x][point.y];

			if (d != null) {
				targetsInDirection.add(d);
			}

			if (dir == Direction.UP)
				point.x++;
			else if (dir == Direction.DOWN)
				point.x--;

			else if (dir == Direction.RIGHT)
				point.y++;

			else
				point.y--;

			i++;
		}

		return targetsInDirection;
	}

	public void specialInteraction(Champion current, Champion target) {

		boolean notSpecial = ((current instanceof Hero && target instanceof Hero)
				|| (current instanceof Villain && target instanceof Villain)
				|| (current instanceof AntiHero && target instanceof AntiHero));

		if (notSpecial)
			target.setCurrentHP(target.getCurrentHP() - current.getAttackDamage());

		else
			target.setCurrentHP(target.getCurrentHP() - (int) (1.5 * current.getAttackDamage()));
	}
	public boolean inFirstTeam(Champion c) {
		return firstPlayer.getTeam().contains(c);
	}

	public void removeDead(Damageable d) {
		Point p = d.getLocation();
		this.board[p.x][p.y] = null;
		if (d instanceof Champion) {
			Champion champion = (Champion) d;
			champion.setCondition(Condition.KNOCKEDOUT);
			if (inFirstTeam(champion))
				firstPlayer.getTeam().remove(champion);
			else
				secondPlayer.getTeam().remove(champion);
			removeFromQueue(champion);

		}
	}

	public void removeFromQueue(Champion c) {
		ArrayList<Champion> a = new ArrayList<Champion>();
		while (!turnOrder.isEmpty())
			a.add((Champion) turnOrder.remove());
		a.remove(c);
		for (Champion d : a) {
			turnOrder.insert(d);
		}

	}



	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public Object[][] getBoard() {
		return board;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}


}


