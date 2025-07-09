package training;

import java.util.*;


//Player

enum PlayerClass {
    WARRIOR(10, 8, 100),
    MAGE(2, 12, 60),
    ROGUE(5, 10, 80),
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
    private int currentHealth;
    private int currentArmor;
    private int currentdmg;;

    public Player(String name, PlayerClass playerClass) {
        this.name = name;
        this.level = 1; // Start at level 1
        this.xp = 0;
        this.playerClass = playerClass;
        this.baseArmor = playerClass.getBaseArmor();
        this.basedmg = playerClass.getBasedmg();
        this.baseHealth = playerClass.getBaseHealth();
        this.currentHealth = baseHealth; // Initialize currentHealth to baseHealth
        this.currentArmor = baseArmor;
        this.currentdmg = basedmg;
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
        if (currentHealth > baseHealth) {
            currentHealth = baseHealth; // Prevent healing above max health
        }
        System.out.println(name + " healed for " + amount + " health. Current health: " + currentHealth);
    }

    // Method to check if the player is alive
    public boolean isAlive() {
        return currentHealth > 0;
    }
    public void attack(Enemy enemy) {
    	enemy.takeDamage(currentdmg);
    }
}

//Enemy

enum EnemyType {
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
	public void attack(Player player) {
		player.takeDamage(attackDamage);
	}
	
}



public class txtRPG {

	public static void main(String[] args) {
		
		
	}

}
