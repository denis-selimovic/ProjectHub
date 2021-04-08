package ba.unsa.etf.nwt.systemevents.service;

import ba.unsa.etf.nwt.systemevents.grpc.*;
import ba.unsa.etf.nwt.systemevents.model.SystemEvent;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Instant;

@GrpcService
public class SystemEventService extends SystemEventServiceGrpc.SystemEventServiceImplBase{

    @Override
    public void log(SystemEventRequest request, StreamObserver<SystemEventResponse> responseObserver) {
        System.out.println("RECEIVED REQUEST");
        SystemEvent systemEvent = new SystemEvent(
                Instant.ofEpochSecond(request.getTimestamp().getSeconds(), request.getTimestamp().getNanos()),
                request.getService(),
                request.getPrincipal(),
                request.getMethod(),
                request.getAction(),
                request.getResource(),
                request.getRequestURL(),
                request.getStatus()
        );
        SystemEventResponse response = SystemEventResponse.newBuilder()
                .setStatus("success")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
