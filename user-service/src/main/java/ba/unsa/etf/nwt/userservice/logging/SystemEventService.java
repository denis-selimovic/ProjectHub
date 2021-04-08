package ba.unsa.etf.nwt.userservice.logging;

import ba.unsa.etf.nwt.systemevents.grpc.SystemEventRequest;
import ba.unsa.etf.nwt.systemevents.grpc.SystemEventResponse;
import ba.unsa.etf.nwt.systemevents.grpc.SystemEventServiceGrpc;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class SystemEventService {

    private final EurekaClient eurekaClient;
    private SystemEventServiceGrpc.SystemEventServiceStub systemEventServiceStub;

    @PostConstruct
    private void createServiceStub() {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("system-events-service", false);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(instanceInfo.getIPAddr(),
                instanceInfo.getPort()).usePlaintext().build();
        systemEventServiceStub = SystemEventServiceGrpc.newStub(channel);
    }

    public void logSystemEvent(SystemEventRequest request) {
        StreamObserver<SystemEventResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(SystemEventResponse systemEventResponse) {
                System.out.println("gRPC response with status: " + systemEventResponse.getStatus());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getLocalizedMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed gRPC call");
            }
        };
        systemEventServiceStub.log(request, responseObserver);
    }
}
