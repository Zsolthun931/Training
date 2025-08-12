package training;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Text coloring
enum TextColor {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String code;

    TextColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
    
}
//Player

enum PlayerClass {
    Warrior(10, 8, 100),
    Mage(2, 12, 60),
    Rogue(5, 10, 80),
    DEFAULT(5, 5, 50); // Default class if none is specified

    private final int baseArmor;
    private final int basedmg;
    private final int baseHealth;

    PlayerClass(int baseArmor, int basedmg, int baseHealth) {
        this.baseArmor = baseArmor;
        this.basedmg = basedmg;
        this.baseHealth = baseHealth;
    }

    public int getBaseArmor() {
        return baseArmor;
    }

    public int getBasedmg() {
        return basedmg;
    }

    public int getBaseHealth() {
        return baseHealth;
    }
}

class Player {
    private String name;
    private int level;
    private int xp;
    private PlayerClass playerClass; 
    private int baseArmor;
    private int basedmg;
    private int baseHealth;
    private int maxHealth;
    private int currentHealth;
    private int currentArmor;
    private int currentdmg;
    private boolean isweaponequipped;
    private boolean isarmorequipped;
    private boolean incombat;

    public Player(String name, PlayerClass playerClass) {
        this.name = name;
        this.level = 1; // Start at level 1
        this.xp = 0;
        this.playerClass = playerClass;
        this.baseArmor = playerClass.getBaseArmor();
        this.basedmg = playerClass.getBasedmg();
        this.baseHealth = playerClass.getBaseHealth();
        this.currentHealth = baseHealth; // Initialize all current stats to base
        this.currentArmor = baseArmor;
        this.currentdmg = basedmg;
        this.maxHealth = baseHealth;
        this.isarmorequipped = false;
        this.isweaponequipped = false;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public int getBaseArmor() {
        return baseArmor;
    }

    public int getBasedmg() {
        return basedmg;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public int getCurrentArmor() {
        return currentArmor;
    }
    
    public int getCurrentdmg() {
        return currentdmg;
    }
    
    public boolean checkAnyWeaponEquipped() {
        return isweaponequipped;
    }
    public boolean checkAnyArmorEquipped() {
        return isarmorequipped;
    }
    public boolean isIncombat() {
		return incombat;
	}
    
    // Setters
    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setBaseArmor(int baseArmor) {
        this.baseArmor = baseArmor;
    }

    public void setBasedmg(int basedmg) {
        this.basedmg = basedmg;
    }

    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    public void setCurrentArmor(int currentdmg) {
        this.currentdmg = currentdmg;
    }
    public void setCurrentdmg(int currentdmg) {
        this.currentdmg = currentdmg;
    }
    public void setIncombat(boolean incombat) {
		this.incombat = incombat;
	}
    public void setPlayerClass(PlayerClass playerClass) {
		this.playerClass = playerClass;
	}
    
    
    
    //Change state of the Weapon slot
    public void setAnyEquippedWeapon() {
        if (this.checkAnyWeaponEquipped()==false)
        {
        	this.isweaponequipped=true;
        }
        else if (this.checkAnyWeaponEquipped()==true) 
        {
        	this.isweaponequipped=false;
        }
    }
    //Change state of the Armor Slot
    public void setAnyEquippedArmor() {
        if (this.checkAnyArmorEquipped()==false)
        {
        	this.isarmorequipped=true;
        }
        else if (this.checkAnyArmorEquipped()==true) 
        {
        	this.isarmorequipped=false;
        }
    }
    
    // Method to check for levelup chance
    public void possiblelevelUp() {
    	if (this.xp==200*level) 
    	{
    		levelUp();
    	}
    }
    
    // Method to level up
    public void levelUp() {
        this.level++;
        //warrior
        if (this.playerClass==PlayerClass.Warrior)
        {
        	this.baseArmor += 3;
        	this.basedmg +=2;
        	this.baseHealth +=20;
        }
        else if (this.playerClass==PlayerClass.Rogue)
        {
        	this.baseArmor += 2;
        	this.basedmg +=3;
        	this.baseHealth +=15;
        }
        else if (this.playerClass==PlayerClass.Mage)
        {
        	this.baseArmor += 1;
        	this.basedmg +=5;
        	this.baseHealth +=10;
        }
        
        this.currentHealth = baseHealth; // Refill health on level up
        
        
        System.out.println(name + " has reached level " + level + "!");
    }

    // Method to take damage
    public void takeDamage(int damage) {
        int actualDamage = Math.max(0, damage - currentArmor); // Apply armor
        currentHealth -= actualDamage;
        if (currentHealth < 0) {
            currentHealth = 0; // Prevent negative health
        }
        System.out.println(name + " took " + actualDamage + " damage. Current health: " + currentHealth);
    }

    // Method to heal
    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth; // Prevent healing above max health
        }
        System.out.println(name + " healed for " + amount + " health. Current health: " + currentHealth);
    }

    // Method to check if the player is alive
    public boolean isAlive() {
        return currentHealth > 0;
    }
    
    
    //Attack enemy
    public void attack(Enemy enemy) {
    	if (this.incombat)
    	{
    	enemy.takeDamage(currentdmg);
	    	if (enemy.getCurrentHealth()<=0)
	    	{
	    		enemy.isAlive=false;
	    		System.out.println("you have defeated a "+enemy.getEnemyName()+" You get "+(enemy.getEnemyLevel()*20 )+"XP" );
	    		this.setXp(this.xp+=(enemy.getEnemyLevel()*20) );
	    		this.possiblelevelUp();
	    		
	    	}
    	}
    	else {
    		System.out.println("You are not in combat!");
    	}
    }
    
    public void equipItemWeapon(Weapon weapon) {
    	if (!weapon.isEquipped() && this.checkAnyWeaponEquipped()==false && weapon.isAcquired()==true) {
	    	if (weapon.getType()==ItemType.WEAPON) 
	    	{
	    	currentdmg += weapon.getDamage();	
	    	this.setAnyEquippedWeapon();
	    	weapon.equip();
	    	System.out.println();
	    	System.out.println("Weapon equipped!");
	    	}
    	}
    	else if (this.checkAnyWeaponEquipped()==true) {
    		System.out.println("Unequip your weapon first!");
    	}
    	
    }
    public void equipItemArmor(Armor armor) {
    	if (!armor.isEquipped() && this.checkAnyArmorEquipped()==false && armor.isAcquired()) {
    		if (armor.getType()==ItemType.ARMOR) 
    		{
    		currentArmor += armor.getBonusArmor();	
    		maxHealth+= armor.getBonusHealth();
    		this.setAnyEquippedArmor();
    		System.out.println();
    		System.out.println("Armor equipped!");
    		armor.equip();
    		}
    	}
    	else if (this.checkAnyArmorEquipped()==true) {
    		System.out.println("Unequip your armor first!");
    	}
    }
    
    public void unEquipItemWeapon(Weapon weapon) {
    	if (weapon.isEquipped()==true && this.checkAnyWeaponEquipped()==true && weapon.isAcquired()==true) {
	    	if (weapon.getType()==ItemType.WEAPON) 
	    	{
	    	currentdmg -= weapon.getDamage();	
	    	this.setAnyEquippedWeapon();
	    	weapon.unequip();
	    	System.out.println();
	    	System.out.println("Weapon unequipped!");
	    	}
	    	else if (this.checkAnyArmorEquipped()==true && weapon.isEquipped()==false)
	    	{
	    		System.out.println("This is not the currently equipped weapon!");
	    	}
	    	else if (this.checkAnyArmorEquipped()==false)
	    	{
	    		System.out.println("you do not have anything equipped in your weapon slot!");
	    	}
    	}
    	
    }
    
    public void unEquipItemArmor(Armor armor) {
    	if (armor.isEquipped()==true && this.checkAnyArmorEquipped()==true && armor.isAcquired()) {
    		if (armor.getType()==ItemType.ARMOR) 
    		{
    		currentArmor -= armor.getBonusArmor();	
    		maxHealth -= armor.getBonusHealth();
    		if (currentHealth>maxHealth) {
    			currentHealth=maxHealth;
    		}
    		this.setAnyEquippedArmor();
    		armor.equip();
    		System.out.println();
    		System.out.println("Armor unequipped!");
    		}
    	}
    	else if (this.checkAnyArmorEquipped()==true && armor.isEquipped()==false)
    	{
    		System.out.println("This is not the currently equipped armor!");
    	}
    	else if (this.checkAnyArmorEquipped()==false)
    	{
    		System.out.println("you do not have anything equipped in your armor slot!");
    	}
    	
    }
    
    //Display the player's current stats
    public void displayCurrentStats() {
    	System.out.println("Name: "+name);
    	System.out.println("Current HP: "+currentHealth);
    	System.out.println("Max HP: "+maxHealth);
    	System.out.println("Current Armor: "+currentArmor);
    	System.out.println("Current dmg: "+currentdmg);
    	System.out.println("level: "+level);
    	System.out.println("XP: "+xp);
    	System.out.println("Class: "+playerClass);
    }
    
    
    //Player debug stat checks
    public void debugShowBaseStats() {
    	System.out.println("Base HP: "+baseHealth);
    	System.out.println("BaseArmor: "+baseArmor);
    	System.out.println("Basedmg: "+basedmg);
    	System.out.println("level: "+level);
    	System.out.println("XP: "+xp);
    	System.out.println("Class: "+playerClass);
    }
    public void debugShowCurrentStats() {
    	System.out.println("Current HP: "+currentHealth);
    	System.out.println("Max HP: "+maxHealth);
    	System.out.println("Current Armor: "+currentArmor);
    	System.out.println("Current dmg: "+currentdmg);
    	System.out.println("level: "+level);
    	System.out.println("XP: "+xp);
    	System.out.println("Class: "+playerClass);
    }
    
    public void debugWeaponCheck() {
    	System.out.println(this.isweaponequipped);
    }
    public void debugArmorCheck() {
    	System.out.println(this.isarmorequipped);
    }
}













//Enemy

enum EnemyType {
	//Enemy format: Name,defense,attackdamage,health,level
    Goblin("Goblin",2, 4, 20,2),
    Orc("Orc",6, 7, 50,5),
    SkeletonWarrior("Skeleton Warrior",3, 5, 35,3),
    SkeletornArcher("Skeleton Archer",1,7,30,3),
    Lich("Lich",4,10,50,6),
    Wolf("Wolf",4, 6, 40,4);
	
	private final String name;
    private final int defense;
    private final int attackDamage;
    private final int baseHealth;
    private final int level;

    EnemyType(String name,int defense, int attackDamage, int baseHealth,int level) {
    	this.name = name;
        this.defense = defense;
        this.attackDamage = attackDamage;
        this.baseHealth = baseHealth;
        this.level = level;
    }
    
    public String getEnemyName() {
    	return name;
    }
    
    public int getDefense() {
        return defense;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getBaseHealth() {
        return baseHealth;
    }
    public int getEnemyLevel( ) {
    	return level;
    }
}

	class Enemy {
		private String name;
		private int level;
		private int baseHealth;
		private int currentHealth;
		private int attackDamage;
		private int defense;
		private EnemyType enemyType;
		//private List<Item> lootPool; //lootpool
		boolean isAlive;
		public Enemy[] orc;
		
		public Enemy(EnemyType enemyType) {
			this.name = enemyType.getEnemyName();
			this.level = enemyType.getEnemyLevel();
			this.baseHealth = enemyType.getBaseHealth();
			this.defense = enemyType.getDefense();
			this.attackDamage = enemyType.getAttackDamage();
			this.currentHealth= baseHealth;
			this.isAlive=true;
			//this.lootPool = enemyType.getLootPool();  //lootpool
		}
		//Getters
		public String getEnemyName() {
			return name;
		}
		public int getEnemyLevel() {
			return level;
		}
		public int getBaseHealth() {
			return baseHealth;
		}
		public int getDefense() {
			return defense;
		}
		public int getAttackDamage() {
			return attackDamage;
		}
		public int getCurrentHealth() {
			return currentHealth;
		}
		public EnemyType getEnemyType() {
			return enemyType;
		}
		//TODO: lootpool getter
		
		//Setters
		public void setEnemyName(String name) {
			this.name= name;
		}
		public void setEnemyLevel(int level) {
			this.level= level;
		}
		public void setBaseHealth(int baseHealth) {
			this.baseHealth=baseHealth ;
		}
		public void setDefense(int defense) {
			this.defense= defense;
		}
		public void setAttackDamage(int attackDamage) {
			this.attackDamage= attackDamage;
		}
		public void setCurrentHealth(int currentHealth) {
			this.currentHealth= currentHealth;
		}
		public void setEnemyType(EnemyType enemyType) {
			this.enemyType = enemyType;
		}
		//TODO: lootpool setter
		
		
		public void takeDamage(int damage) {
	        int actualDamage = Math.max(0, damage - defense); // Apply armor
	        currentHealth -= actualDamage;
	        if (currentHealth <= 0) {
	            currentHealth = 0; // Prevent negative health
	            this.isAlive=false;
	        }
	        System.out.println(name + " took " + actualDamage + " damage. Current health: " + currentHealth);
	    }
		
		public void heal(int amount) {
	        currentHealth += amount;
	        if (currentHealth > baseHealth) {
	            currentHealth = baseHealth; // Prevent healing above max health
	        }
	        System.out.println(name + " healed for " + amount + " health. Current health: " + currentHealth);
	    }
		//Enemy attack the current player
		public void attack(Player player) {
			player.takeDamage(attackDamage);
		}
		
		//Award xp when dead
		public void awardXP(Player player) {
			player.setXp( (player.getXp()+this.level*10) );
		}
		
		//Reset to be able to fight multiple times
		public void enemyReset() {
			this.currentHealth=this.baseHealth;
			this.isAlive=true;
		}
		
	}
	
	
	
	
	
	
	
	
	
	

//Items

enum ItemType {
    WEAPON,
    ARMOR,
    CONSUMABLE,
    MISCELLANEOUS
}
enum ItemRarity {
    Common,
    Uncommon,
    Rare,
    Epic,
    Legendary
}
   
class Item {
	private String name;
	private ItemType itemtype;
	private ItemRarity itemrarity;
	private boolean stackable;
	private boolean equipped;
	private boolean acquired;
	
	//name,ItemType,ItemRarity,stackable
	public Item(String name,ItemType itemtype,ItemRarity itemrarity,boolean stackable) {
		this.name=name;
		this.itemtype=itemtype;
		this.itemrarity=itemrarity;
		this.stackable=stackable;
		this.equipped=false;
		this.acquired=true; //default true for now until drop loot is implemented
	}
	
	
	
	//Getters
	public String getName() {
		return name;
	}
	public ItemType getType() {
		return itemtype;
	}
	public ItemRarity getRarity() {
		return itemrarity;
	}
	public boolean isStackable() {
		return stackable;
	}
	public boolean isEquipped() {
		return equipped;
	}
	public boolean isAcquired() {
		return acquired;
	}
	
	//setters
	public void setName(String name) {
		this.name=name;
	}
	public void equip() {
		this.equipped=true;
	}
	public void unequip() {
		this.equipped=false;
	}
	public void setAcquired(boolean acquired) {
		this.acquired = acquired;
	}
	
	 @Override
	    public String toString() {
	        return "Item{" +
	                "name='" + name + '\'' +
	                ", type=" + itemtype +
	                ", rarity=" + itemrarity +
	                ", stackable=" + stackable +
	                ", equipped=" + equipped +
	                '}';
	    }
	 
}

//Weapon class, extending Item
class Weapon extends Item {
 private int damage;
 private String weaponType; // e.g., "Helmet", "Shield"

 // Constructor
 public Weapon(String name, ItemRarity rarity, int damage, String weaponType) {
     super(name, ItemType.WEAPON, rarity, false); // Armor are generally not stackable
     this.damage = damage;
     this.weaponType = weaponType;
 }

 // Getters
 public int getDamage() {
     return damage;
 }

 public String getWeaponType() {
     return weaponType;
 }

 

 @Override
 public String toString() {
     return "Weapon{" +
             "name='" + getName() + '\'' +
             ", type=" + getType() +
             ", rarity=" + getRarity() +
             ", damage=" + damage +
             ", weaponType='" + weaponType + '\'' +
             ", equipped=" + isEquipped() +
             '}';
 }
}








//Armor class, extending Item
class Armor extends Item {
private int bonusarmor;
private int bonushealth;
private String armorType; // e.g., "Armor", "Helmet"

// Constructor
public Armor(String name, ItemRarity rarity, int bonusarmor, int bonushealth, String armorType) {
   super(name, ItemType.ARMOR, rarity, false); // Armor are generally not stackable
   this.bonusarmor = bonusarmor;
   this.bonushealth = bonushealth;
   this.armorType = armorType;
}

// Getters
public int getBonusArmor() {
   return this.bonusarmor;
}
public int getBonusHealth() {
		return this.bonushealth;
	}

public String getArmorType() {
   return this.armorType;
}



@Override
public String toString() {
   return "Armor{" +
           "name='" + getName() + '\'' +
           ", type=" + getType() +
           ", rarity=" + getRarity() +
           ", bonusarmor=" + bonusarmor +
           ", bonushealth=" + bonushealth +
           ", armorType='" + armorType + '\'' +
           ", equipped=" + isEquipped() +
           '}';
}
}












//Consumable class, extending Item
class Consumable extends Item {
private int healamount;
private String consumableType; // e.g., "Potion", "Food"

//Constructor
public Consumable(String name, ItemRarity rarity, int healamount, String consumableType) {
 super(name, ItemType.CONSUMABLE, rarity, true); // Consumable are generally stackable
 this.healamount = healamount;
 this.consumableType = consumableType;
}

//Getters
public int getHealAmount() {
 return this.healamount;
}

public String getConsumableType() {
 return this.consumableType;
}



@Override
public String toString() {
 return "Consumable{" +
         "name='" + getName() + '\'' +
         ", type=" + getType() +
         ", rarity=" + getRarity() +
         ", healamount=" + healamount +
         ", consumableType='" + consumableType + '\'' +
         ", equipped=" + isEquipped() +
         '}';
}
}

//All locations
class Location {
	private String name;
	private int ID;
	private String description;
	private boolean currentlocation;
	
	
	public Location(String name,int ID,String description)
	{
		this.name = name;
		this.ID = ID;
		this.description = description;
	}
	
	//Getters
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getID() {
		return ID;
	}
	
	//Setters
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void changeLocation(Location newloc) {
		this.currentlocation=false;
		newloc.currentlocation=true;
	}
	
	@Override
	public String toString() {
	 return "Location{" +
	         "Name:'" + getName() + '\'' +
	         ", Description:" + getDescription() + '}'
	         ;
	}
}

//Locations that has enemies
class EnemyLocation extends Location {
	private Enemy[] enemies;
	
	public EnemyLocation(String name, int ID, String description,Enemy[] enemies) {
		super(name, ID, description);
		this.enemies = enemies;
	}
	public Enemy[] getEnemies() {
		return enemies;
	}
	public void setEnemies(Enemy[] enemies) {
		this.enemies = enemies;
	}
	
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < enemies.length; i++) {
            sb.append(enemies[i].getEnemyName());
            if (i < enemies.length - 1) {
                sb.append(", "); // Add comma and space if not the last element
            }
        }
        
	 return "Location{" +
	         "Name:'" + getName() + '\'' +
	         ", Description:" + getDescription() 
	         + ", Enemies: " + sb.toString()
	         +'}';
	 
	         
	}
	
}

public class txtRPG {

	public static void main(String[] args) {
		boolean run =true;
		
		Scanner sc=new Scanner(System.in);
		String command="";
		boolean cancommand=true;
		String chosenclass="";
		
		//Initialise player
		Player player = new Player("Tav", PlayerClass.Warrior);
		//Initialise enemies
		 Enemy Goblin = new Enemy(EnemyType.Goblin);
		 Enemy Orc = new Enemy(EnemyType.Orc);
		 Enemy Skeleton = new Enemy(EnemyType.SkeletonWarrior);
		 Enemy Wolf = new Enemy(EnemyType.Wolf);
		 String[] enemynames =new String[4];
		 
		 //set Locations
		 Location MainMenu = new Location("Main Menu",0,"Main Menu");//Start here
		 Location Map = new Location("Map",1,"This is the map");
		 Location Shop = new Location("Shop",2,"This is the shop"); //in game shop later
		 Enemy[] forestE = new Enemy[2]; 
		 forestE[0]=Goblin;
		 forestE[1]=Wolf;
		 EnemyLocation Forest = new EnemyLocation("Forest",3,"An old forest",forestE); //Starting enemy location
		 
		 Enemy[] graveyardE = new Enemy[2]; 
		 graveyardE[0]=Goblin;
		 graveyardE[1]=Wolf;
		 EnemyLocation Graveyard = new EnemyLocation("Graveyard",4,"Cursed Graveyard where undead walks",graveyardE); //Starting enemy location
		 
		 //menu commands
		 //attack,heal,map,equip,unequip,stats,commands,help,move
		 String[] commands = new String[10];
		 commands[0] ="commands";
		 commands[1] ="help";
		 commands[2] ="attack";
		 commands[3] ="heal";
		 commands[4] ="map";
		 commands[5] ="equip";
		 commands[6] ="unequip";
		 commands[7] ="stats";
		 commands[8] ="exit";
		 commands[9] ="move";
		 
		 
		 
		 
		 //weapons
		 Weapon woodensword = new Weapon("Wooden Sword",ItemRarity.Common,1,"Sword");
		 Weapon ironsword = new Weapon("Iron Sword",ItemRarity.Uncommon,3,"Sword");
		 Weapon steelsword = new Weapon("Steel Sword",ItemRarity.Rare,5,"Sword");
		 Weapon darksteelsword = new Weapon("Dark Steel Sword",ItemRarity.Rare,8,"Sword");
		 Weapon chaosironsword = new Weapon("Chaos Iron Sword",ItemRarity.Epic,12,"Sword");
		 Weapon destroyer = new Weapon("Destroyer",ItemRarity.Legendary,20,"Sword");
		 
		 
		 //armor
		 Armor clotharmor = new Armor("Cloth armor",ItemRarity.Common,1,0,"Armor");
		 Armor leatherharmor = new Armor("Leather armor",ItemRarity.Common,2,10,"Armor");
		 Armor ironarmor = new Armor("Iron armor",ItemRarity.Uncommon,3,25,"Armor");
		 Armor steelharmor = new Armor("Steel armor",ItemRarity.Common,5,40,"Armor");
		 Armor chaosironarmor = new Armor("Chaos Iron armor",ItemRarity.Uncommon,10,70,"Armor");
		 Armor juggernaut = new Armor("Juggernaut",ItemRarity.Common,20,150,"Armor");
		 
		 System.out.println("Welcome to this little txt based RPG");
		 System.out.println("Please tell me your character's name");
		 String playername= sc.nextLine();
		 String classchoice ="";
		 while (!classchoice.equalsIgnoreCase("warrior") && !classchoice.equalsIgnoreCase("rogue") && !classchoice.equalsIgnoreCase("mage")) {
	            System.out.println("Which class would you like to play? \n Classes: Warrior, Rogue, Mage");
	            classchoice = sc.nextLine();
	            classchoice.toLowerCase();
	            if (classchoice.equalsIgnoreCase("warrior")) {
	                System.out.println("You have chosen "+TextColor.RED+"Warrior"+TextColor.RESET);
	                
	                player.setPlayerClass(PlayerClass.Warrior);
	                
	                
	                
	            
	            }//warrior end here
	               else if (classchoice.equalsIgnoreCase("rogue")) {
	                System.out.println("You have chosen "+TextColor.GREEN+ "Rogue"+TextColor.RESET);
	                
	                player.setPlayerClass(PlayerClass.Rogue);
	                
	                
	            } else if (classchoice.equalsIgnoreCase("mage")) {
	                System.out.println("You have chosen "+TextColor.CYAN+ "Mage"+TextColor.RESET);
	                
	                player.setPlayerClass(PlayerClass.Mage);
	                
	            } else {
	                System.out.println("Invalid class, please type in a class you want to choose and it is on the playable classes list \n");
	            }
	        }
		 
		 while (run) {
   			 
   			 
   			 while (cancommand==true) {
   				 System.out.println("Now what would you like to do? \nPlease enter a command or write \"help\" for the commands list");
   		            command = sc.nextLine();
   		            command.toLowerCase();
   		            
	   		            if (command.equals("help") )
	   		            {
	   		            	System.out.println("You can use these commands: \n help, commands, attack, move, heal, map, equip, unequip, stats, exit");
	   		            	
	   		            }
	   		            else if (command.equals("commands") )
			            {
			            	System.out.println("You can use these commands: \n help, commands, attack, move, heal, map, equip, unequip, stats, exit");
			            	
			            }
	   		            else if (command.equals("attack") )
			            {
	   		            	System.out.println("who do you want to attack?");
	   		            	command=sc.nextLine();
	   		            	
			            }
	   		            else if (command.equals("move") )
			            {
			            	if (!player.isIncombat())
			            	{
			            		System.out.println("Where do you want to go?");
			            	}
			            	else if (player.isIncombat())
			            	{
			            		System.out.println("you are in combat, you cannot go away \n fight like a soldier!");
			            	}
			            }
	   		            else if (command.equals("heal") )
			            {
				            	
			            }
	   		            else if (command.equals("map") )
	   		            {
				            	
	   		            }
				   		else if (command.equals("equip") )
				        {
				           	
				        }
				   		else if (command.equals("unequip") )
				        {
				           	
				        }
				   		else if (command.equals("stats") )
				        {
				           	
				        }
   		            
   		            
   		            

   		            else if (command.equalsIgnoreCase("exit")) {
   		            	System.out.println("Exiting game, Goodbye");
   		            	cancommand=false;
   		                run=false; //stop game
   		            }

   		            
   		        }
            
            
        } //Run ends here
			 
			
		
		 
	
	
	
	}//main ends here
	
	public void changePlayerLocation(Location currentlocation,Location newlocation) {
		currentlocation.changeLocation(newlocation);
	}
	
	
	
	
	
	

}//txtrpg ends here
