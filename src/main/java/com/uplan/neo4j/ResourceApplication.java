//package com.uplan.neo4j;
//
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.Transaction;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//import org.neo4j.kernel.impl.util.FileUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
//import org.springframework.data.neo4j.config.Neo4jConfiguration;
//import org.springframework.data.neo4j.core.EntityPath;
//import org.springframework.data.neo4j.core.GraphDatabase;
//
//import java.io.File;
//
///**
//* Created by tanvimehta on 15-01-18.
//*/
//@Configuration
//@EnableNeo4jRepositories(basePackages = "com.uplan.neo4j")
//public class ResourceApplication extends Neo4jConfiguration implements CommandLineRunner{
//
//    public ResourceApplication() {
//        setBasePackage("com.uplan.neo4j");
//    }
//
//    @Bean
//    GraphDatabaseService graphDatabaseService() {
//        return new GraphDatabaseFactory().newEmbeddedDatabase("accessingdataneo4j.db");
//    }
//
//    @Autowired
//    ResourceRepository resourceRepository;
//
//    @Autowired
//    GraphDatabase graphDatabase;
//
//    public void run(String... args) throws Exception {
//        ResourceNode room1 = new ResourceNode(1, "room","R1");
//        ResourceNode room2 = new ResourceNode(3, "room", "R2");
//        ResourceNode room3 = new ResourceNode(4, "room", "R3");
//        ResourceNode room4 = new ResourceNode(5, "room", "R4");
//        ResourceNode room5 = new ResourceNode(6, "room", "R5");
//        ResourceNode corridor1 = new ResourceNode(2, "corridor", "C1");
//        ResourceNode corridor2 = new ResourceNode(7, "corridor", "C2");
//        ResourceNode corridor3 = new ResourceNode(8, "corridor", "C3");
//
////        System.out.println("Before linking up with Neo4j...");
////        for (ResourceNode resource : new ResourceNode[]{room1, corridor, room2}) {
////            System.out.println(resource);
////        }
//
//        Transaction tx = graphDatabase.beginTx();
//        try {
//            resourceRepository.save(room1);
//            resourceRepository.save(room2);
//            resourceRepository.save(room3);
//            resourceRepository.save(room4);
//            resourceRepository.save(room5);
//            resourceRepository.save(corridor1);
//            resourceRepository.save(corridor2);
//            resourceRepository.save(corridor3);
//
//            room1 = resourceRepository.findByResourceId(room1.getResourceId());
//            room1.connectsTo(corridor1);
//            resourceRepository.save(room1);
//
//            corridor1 = resourceRepository.findByResourceId(corridor1.getResourceId());
//            corridor1.connectsTo(room2);
//            corridor1.connectsTo(room3);
//            resourceRepository.save(corridor1);
//
//            room2 = resourceRepository.findByResourceId(room2.getResourceId());
//            room2.connectsTo(corridor2);
//            resourceRepository.save(room2);
//
//            corridor2 = resourceRepository.findByResourceId(corridor2.getResourceId());
//            corridor2.connectsTo(room5);
//            resourceRepository.save(corridor2);
//
//            room5 = resourceRepository.findByResourceId(room5.getResourceId());
//            room5.connectsTo(room4);
//            resourceRepository.save(room5);
//
//            room4 = resourceRepository.findByResourceId(room4.getResourceId());
//            room4.connectsTo(corridor3);
//            resourceRepository.save(room4);
//
//            corridor3 = resourceRepository.findByResourceId(corridor3.getResourceId());
//            corridor3.connectsTo(room3);
//            resourceRepository.save(corridor3);
//
////            System.out.println("Lookup each location by id...");
////            for (Long resourceId: new Long[]{room1.getResourceId(), corridor.getResourceId(), room2.getResourceId()}) {
////                System.out.println(resourceRepository.findByResourceId(resourceId));
////            }
//
//            System.out.println("///////////SHORTEST PATH///////////");
//            System.out.println("///////////////////////////////////");
//            for(EntityPath<ResourceNode, ResourceNode> entityPath: resourceRepository.getPath(room1, room4)){
//                Iterable<ResourceNode> rnodes = entityPath.nodeEntities();
//                for (ResourceNode rnode: rnodes) {
//                    System.out.print(rnode.getResourceName() + "->");
//                }
//                System.out.println();
//                System.out.println("///////////////////////////////");
//            }
//
////            PathFinder<Path> finder = GraphAlgoFactory.shortestPath(PathExpanders.forTypeAndDirection(new RelationshipType() {
////                @Override
////                public String name() {
////                    return "CONNECTS_TO";
////                }
////            }, Direction.OUTGOING), 15);
////
////
////            finder.findSinglePath(room1, room2);
////            System.out.println("Looking up what connects with room2...");
////            for (ResourceNode resource : resourceRepository.findByNeighboursResourceId(room2.getResourceId())) {
////                System.out.println(resource.getResourceName() + " connects to room2.");
////            }
//
//            tx.success();
//        } finally {
//            tx.close();
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        FileUtils.deleteRecursively(new File("accessingdataneo4j.db"));
//
//        SpringApplication.run(ResourceApplication.class, args);
//    }
//
//}
