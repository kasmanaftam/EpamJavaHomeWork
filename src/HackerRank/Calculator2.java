package HackerRank;

class Calculator2 implements AdvancedArithmetic{
    public int divisorSum(int n){
        int acc=0;
        for(int i=1; i<=n; i++){
            if(n%i==0) acc+=i;
        }
        return acc;
    }
}
