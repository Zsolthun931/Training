package training;

import java.util.*;


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
    
    // Method to level up
    public void levelUp() {
        this.level++;
        // Increase stats
        this.baseArmor += 2;
        this.basedmg += 3;
        this.baseHealth += 20;
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
    	enemy.takeDamage(currentdmg);
    }
    
    public void equipItemWeapon(Weapon weapon) {
    	if (!weapon.isEquipped() && this.checkAnyWeaponEquipped()==false) {
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
    	if (!armor.isEquipped() && this.checkAnyArmorEquipped()==false) {
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
    	if (weapon.isEquipped()==true && this.checkAnyWeaponEquipped()==true) {
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
    	if (armor.isEquipped()==true && this.checkAnyArmorEquipped()==true) {
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
    Skeleton("Skeleton",3, 5, 35,3),
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
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	

//Items

enum ItemType {
    WEAPON,
    ARMOR,
    CONSUMABLE,
    MISCELLANEOUS
}
enum ItemRarity {
    COMMON,
    UNCOMMON,
    RARE,
    EPIC,
    LEGENDARY
}
   
class Item {
	private String name;
	private ItemType itemtype;
	private ItemRarity itemrarity;
	private boolean stackable;
	private boolean equipped;
	
	
	public Item(String name,ItemType itemtype,ItemRarity itemrarity,boolean stackable) {
		this.name=name;
		this.itemtype=itemtype;
		this.itemrarity=itemrarity;
		this.stackable=stackable;
		this.equipped=false;
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





public class txtRPG {

	public static void main(String[] args) {
		
		 Player warriorPlayer = new Player("Sir Reginald", PlayerClass.Rogue);
		 Weapon sword=new Weapon("Iron Sword",ItemRarity.COMMON,5,"Sword");
		 Armor ironarmor=new Armor("Iron Armor",ItemRarity.COMMON,3,10,"Armor");
		 warriorPlayer.debugShowBaseStats();
		 System.out.println();
		 warriorPlayer.equipItemWeapon(sword);
		 System.out.println();
		 warriorPlayer.equipItemArmor(ironarmor);
		 warriorPlayer.debugShowCurrentStats();
		 
		 
	}

}
