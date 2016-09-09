public class RoutingMapTree
{
	private Exchange root;
	public RoutingMapTree()
	{
		root=new Exchange(0);
	}		
	public RoutingMapTree(int n)
	{
		root=new Exchange(n);
	}
	public boolean containsNode(Exchange e)
	{
		return containsNode(e.getIdfr());
	}
	public boolean containsNode(int n)
    {
		return (findExchange(n)!=null);
	}
		
	public void switchOff(MobilePhone a)
	{
		if (!a.status())
			System.out.println("MobilePhone is already switched off!!!");
		else		
            {a.switchOff();
                Exchange tmp=a.location();
                while (tmp!=null)
                {
                    tmp.residentSet().Delete(a);
                    tmp=tmp.parent();
                }
            }
    }
	public void switchOn(MobilePhone a, Exchange b)
	{
		if (a.status())
			System.out.println("MobilePhone is already switched on!!!!");
		else 
		{
			a.switchOn();
			if (a.location()!=null)
			{
				Exchange x=a.location();
				while (x!=null)
				{
					x.residentSet().Delete(a);
					x=x.parent();
				}
			}	
			a.setStation(b);
			Exchange e=b;
			while (e!=null)
			{
				e.residentSet().Insert(a);
				e=e.parent();
			}
		}
	}
	
	public Exchange findExchange(Exchange e, int n)
	{
		if (e.getIdfr()==n)
			return e;
		else
		{			
			for (int i=0;i<e.numChildren();i++)
			{
				Exchange x=findExchange(e.child(i),n);
				if (x!=null)
					return x; 
			}
		}
		return null;
	}
	public Exchange findExchange(int n)
	{
		return findExchange(root,n);
	}
	public void print(String s)
	{
		System.out.print(s+":");		
	}
	public void performAction(String actionMessage) 
    {  int firstInt,secondInt;
        firstInt=0;
        secondInt=0;
		int no_spaces=0;
		for (int i=0;i<actionMessage.length();i++)
		{
			if (actionMessage.charAt(i)==' ')
               no_spaces++;
        }
		if(no_spaces==1)
        {for (int i=0;i<actionMessage.length();i++)
            if(actionMessage.charAt(i)==' ')
            {    firstInt=Integer.parseInt(actionMessage.substring(i+1,actionMessage.length()));
                break;
            }
        }
         if(no_spaces==2)
         {for (int i=0;i<actionMessage.length();i++)
             if(actionMessage.charAt(i)==' ')
            {for (int j=i+1;j<actionMessage.length();j++)
				{
					if (actionMessage.charAt(j)==' ')
                    {	firstInt=Integer.parseInt(actionMessage.substring(i+1,j));
                    secondInt=Integer.parseInt(actionMessage.substring(j+1,actionMessage.length()));
						break;
					}
				}
				
            
			}
		}		
		if (actionMessage.substring(0,11).equals("addExchange"))
        {
			if (!containsNode(firstInt))
               System.out.println("MobilePhone"+firstInt+"does not exist");            
            else
			{
				Exchange child=new Exchange(secondInt);
				child.setParent(findExchange(firstInt));								
				findExchange(firstInt).children().Insert(child);
               
			}
		}
		else if (actionMessage.substring(0,14).equals("switchOnMobile"))
		{			
			if (!containsNode(secondInt))
				System.out.println("MobilePhone"+secondInt+"does not exist");	
			else if(root.findMobile(firstInt)!=null)
			    switchOn(root.findMobile(firstInt),findExchange(secondInt));
			else
			{	MobilePhone m=new MobilePhone(firstInt);
			    switchOn(m,findExchange(secondInt));
			}    
		}
		else if (actionMessage.substring(0,15).equals("switchOffMobile"))	
		{			
			if (root.findMobile(firstInt)==null)
				System.out.println("MobilePhone"+firstInt+"does not exist!!!");
			else			
				switchOff(root.findMobile(firstInt));
		}
		else if (actionMessage.substring(0,13).equals("queryNthChild"))
		{			
			print(actionMessage);
			if (findExchange(firstInt).numChildren()<secondInt)
				System.out.println(secondInt+"th child does not exist!!!");
			else
				System.out.println(findExchange(firstInt).child(secondInt).getIdfr());	
		}
		else if (actionMessage.substring(0,19).equals("queryMobilePhoneSet"))
		{		
			print(actionMessage);	
			Node<Object> o=findExchange(firstInt).residentSet().elementatTop();
			while (o!=null)
			{
				MobilePhone p=(MobilePhone)(o.getElement());
				System.out.print(p.number());
				if(o.getNext()!=null)
					System.out.print(",");
				o=o.getNext();
			}
			System.out.println(" ");
		}
					 
	}
}
			

	
