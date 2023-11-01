import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Snake  extends Actor
{
    private int x,y,d;
    private final int DOT_SIZE=20;
    public Snake(int Snake)
    {
        GreenfootImage img = new GreenfootImage("head.gif");
        img.mirrorHorizontally();
        d = Snake;
        if(d == 0)
        {
            setImage(img);
        }
        else
        {
            setImage("close.png");
        }
    }
    public void act() 
    {
       if(d == 0)
       {
        lead();
        lookForFood();
        lookForEdge();
        lookForDot();
       }
       else
       {
           follow();
       }
    }   

    public void lead()
    {    
        x = getX();
        y = getY();
        if ((Greenfoot.isKeyDown("left") && (getRotation() != 0)))
        {
            setRotation(180);
        }
        else if ((Greenfoot.isKeyDown("right") && (getRotation() != 180)))
        {
            setRotation(0);
        }
        else if ((Greenfoot.isKeyDown("up") && (getRotation() != 90)))
        {
            setRotation(270);
        }else if ((Greenfoot.isKeyDown("down") && (getRotation() != 270)))
        {
            setRotation(90);
        }
        
        stayOnCourse();
        setLocation(x,y);
        SnakeWorld world = (SnakeWorld) getWorld();
        world.setDX(d,x);
        world.setDY(d,y);
    }

    public void follow()
    {
        SnakeWorld world = (SnakeWorld) getWorld();
        x = world.getMyX(d);
        y = world.getMyY(d);
        setLocation(x,y);
    }

    public void growTail()
    {
        SnakeWorld world = (SnakeWorld) getWorld();
        world.addDot();
    }

    public void lookForFood()
    {
        if(canSee(Food.class))
        {
            eat(Food.class);
            growTail();
            SnakeWorld world = (SnakeWorld) getWorld();
            world.addFood();
        }
    }

    public boolean canSee(Class clss)
    {
       Actor actor = getOneObjectAtOffset(0,0,clss); 
       return actor != null;
    }

    public void eat(Class clss)
    {
        Actor actor = getOneObjectAtOffset(0,0,clss);
        if(actor != null)
        {
           getWorld().removeObject(actor); 
        }
    }

    public boolean atWorldEdge()
    {
        if(getX() < 20 || getX() > getWorld().getWidth() - 20)
        {
            return true;
        }
        if(getY() < 20 || getY() > getWorld().getHeight() -20)
        {
            return true;
        }
        else
            return false;
    }

    public void lookForEdge()
    {
        if(atWorldEdge())
        {
            SnakeWorld world = (SnakeWorld) getWorld();
            world.gameOver();
        }
    }

    public void lookForDot()
    {
        if (canSee(Snake.class))
        {
            SnakeWorld world = (SnakeWorld) getWorld();
            world.gameOver();
        }
    }
    
    public void stayOnCourse()
    {
        double angle = Math.toRadians(getRotation());
        x = (int) Math.round(getX() + Math.cos(angle)*DOT_SIZE);
        y = (int) Math.round(getY() + Math.sin(angle)*DOT_SIZE);
    } 
  
}
