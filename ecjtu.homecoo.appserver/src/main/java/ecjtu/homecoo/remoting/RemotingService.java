package ecjtu.homecoo.remoting;

public interface RemotingService {
    public void start();


    public void shutdown();


    public void registerRPCHook(RPCHook rpcHook);
}
