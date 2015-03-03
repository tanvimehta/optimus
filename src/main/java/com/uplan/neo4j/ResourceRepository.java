//package com.uplan.neo4j;
//
//import org.springframework.data.neo4j.annotation.Query;
//import org.springframework.data.neo4j.core.EntityPath;
//import org.springframework.data.neo4j.repository.GraphRepository;
//import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
//import org.springframework.data.repository.CrudRepository;
//
///**
//* Created by tanvimehta on 15-01-18.
//*/
//public interface ResourceRepository extends CrudRepository<ResourceNode, Long>, RelationshipOperationsRepository<ResourceNode>, GraphRepository<ResourceNode> {
//
//    ResourceNode findByResourceId(Long resourceId);
//
//    Iterable<ResourceNode> findByNeighboursResourceId(Long resourceId);
//
//    @Query("START r1=node({0}), r2=node({1}) MATCH p = shortestPath( r1-[*]-r2 ) RETURN p")
//    Iterable<EntityPath<ResourceNode, ResourceNode>> getPath(ResourceNode r1, ResourceNode r2);
//}
