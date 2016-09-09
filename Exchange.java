public class Exchange
{
	private int identifier;
	private Exchange parent;
	private ExchangeList children;
	private MobilePhoneSet m;
	public int getIdfr()
	{
		return identifier;
	}	
	public void setParent(Exchange e)
	{
		parent=e;
	}
	public Exchange(int number)
	{
		identifier=number;
		children=new ExchangeList();
		m=new MobilePhoneSet();
	}		
	public Exchange parent()
	{
		return parent;
	}
	public ExchangeList children()
	{
		return children;
	}
	public int numChildren()
	{
		int n=0;
		Node<Exchange> e=children.getTop();
		while (e!=null)
		{
			n++;
			e=e.getNext();
		}
		return n;
	}	
	public Exchange child(int i)
	{
		int j=numChildren()-1;
		Node<Exchange> e=children.getTop();
		if (j<i)
			return null;		
		while (e!=null)
		{
			if (j==i)
				return e.getElement();
			j--;
			e=e.getNext();
		}
		return null;		
	}
	public boolean isRoot()
	{
		return (parent==null);
	}
	public RoutingMapTree subtree(int i)
	{  Exchange e=child(i);
	   if (e.children()!=null)
	   {		
		   RoutingMapTree r=new RoutingMapTree(e.getIdfr());						
		   for (int k=0;k<e.numChildren();k++)
			   e.subtree(i).Insert(e);
		   return r;
	   }
	   return null;		
	}
	public MobilePhoneSet residentSet()
	{	
		return m;
	}
	public MobilePhone findMobile(int n)
	{
		return m.find(n);
	}
}	

