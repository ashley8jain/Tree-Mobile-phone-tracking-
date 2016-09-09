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
	public void Insert(Exchange e)
	{
		e.children().Insert(root);
		root.setParent(e);
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
            {
                Exchange tmp=a.location();
                while (tmp!=null)
                {
                    tmp.residentSet().Delete(a);
                    tmp=tmp.parent();
                }
                a.switchOff();
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
	public Exchange findPhone(MobilePhone m)
	{       
			return m.location();
	}
	public Exchange findExchange(int n)
	{
		return findExchange(root,n);
	}
	public Exchange lowestRouter(Exchange e1,Exchange e2)
	{   
		while(e1!=null)
		{ int A=e1.getIdfr();
			Exchange tmp=e2;
			while(tmp!=null)
			{   
				int B=tmp.getIdfr();
				if(A==B)
					return tmp;
				tmp=tmp.parent();
			}
			e1=e1.parent();
		}
		return null;
	}
	public ExchangeList routeCall(MobilePhone m1,MobilePhone m2)
	{   Exchange A=findExchange(m1.location().getIdfr());
		Exchange B=findExchange(m2.location().getIdfr());
		Exchange lowstroutr=lowestRouter(A,B);
		ExchangeList A1=new ExchangeList();
		ExchangeList B1=new ExchangeList();
		while(A.getIdfr()!=lowstroutr.getIdfr())
		{
			A1.Insert(A);
			A=A.parent();
		}		
		while(B.getIdfr()!=lowstroutr.getIdfr())
		{
			B1.Insert(B);
			B=B.parent();
		}
		B1.Insert(lowstroutr);
		ExchangeList reverseB1=new ExchangeList();
		Node<Exchange> top=B1.getTop();
		while(top!=null)
		{   reverseB1.Insert(top.getElement());
			top=top.getNext();
		}
		ExchangeList final1=new ExchangeList();
		top=reverseB1.getTop();
		while(top!=null)
		{
			final1.Insert(top.getElement());
			top=top.getNext();
		}
		top=A1.getTop();
		while(top!=null)
		{
			final1.Insert(top.getElement());
			top=top.getNext();
		}
		
		return final1;		
	}
	public void movePhone(MobilePhone a,Exchange b)
	{   switchOff(a);
	    MobilePhone m=new MobilePhone(a.number());
	    switchOn(m,b);
		
	}
	public void print(String s)
	{
		System.out.print(s+": ");		
	}
	public void performAction(String actionMessage) 
    {try{int firstInt,secondInt;
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
         if(actionMessage.substring(0,9).equals("movePhone"))
         {   MobilePhone numb=root.findMobile(firstInt);
         if(numb==null)
        	 throw new RuntimeException("MobilePhone "+firstInt+" is not avialable!!!");
             Exchange station=findExchange(secondInt);        		 
         if(station==null)
        	 throw new RuntimeException("Exchange "+secondInt+" does not exist!!!");
			 movePhone(numb,station);
         }
         else if (actionMessage.substring(0,11).equals("addExchange"))
        {
			if (!containsNode(firstInt))
               throw new RuntimeException("MobilePhone "+firstInt+" does not exist");            
            else
			{
				Exchange child=new Exchange(secondInt);
				child.setParent(findExchange(firstInt));								
				findExchange(firstInt).children().Insert(child);              
			}
		}
         else if(actionMessage.substring(0,12).equals("lowestRouter"))
 		{
 			print("queryLowestRouter "+firstInt+" "+secondInt);
 			Exchange a=findExchange(firstInt);
 			Exchange b=findExchange(secondInt);
 			 Exchange lowstrtr=lowestRouter(a,b);
 			System.out.println(lowstrtr.getIdfr());
 		}
        else if(actionMessage.substring(0,14).equals("queryFindPhone"))	
  		{
  		print(actionMessage);
  		MobilePhoneSet tmp=root.residentSet();
  		MobilePhone find=tmp.find(firstInt);
  		if(find==null)
  			System.out.println("Error - No mobile phone with identifier "+firstInt+" found in the network");
  		else
  		 System.out.println(findPhone(find).getIdfr());
   		}
		else if (actionMessage.substring(0,14).equals("switchOnMobile"))
		{			
			if (!containsNode(secondInt))
				throw new RuntimeException("MobilePhone"+secondInt+"does not exist");	
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
				throw new RuntimeException("MobilePhone"+firstInt+"does not exist!!!");
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
		else if(actionMessage.substring(0,17).equals("queryFindCallPath"))
 		{
 			print(actionMessage);
 			MobilePhone start,end;
 			start=root.residentSet().find(firstInt);
 			end=root.residentSet().find(secondInt);
 			if(start==null)
 				System.out.println("Error - Mobile phone with identifier "+firstInt+" is currently switched off");
 			else if(end==null)
 				System.out.println("Error - Mobile phone with identifier "+secondInt+" is currently switched off");
 			else
 			{	
 				ExchangeList path=routeCall(start,end);
 				Node<Exchange> top=path.getTop();
 				while(top!=null)
 				{   System.out.print(top.getElement().getIdfr());
 					if(top.getNext()!=null)
 						System.out.print(", ");
 					top=top.getNext();
 				}
 				System.out.println(" ");
 			}			
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
		else 
			{ print(actionMessage);
			  System.out.println("Error action message!!!");	
			}
    }
    catch(StringIndexOutOfBoundsException er)
    {   print(actionMessage);
    	System.out.println("Error - Wrong query message!!");
    }
    catch(RuntimeException e){
    	System.out.print(actionMessage+": ");
    	e.printStackTrace();
    }
    }
}
			

	
