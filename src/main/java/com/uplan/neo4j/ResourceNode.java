package com.uplan.neo4j;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

/**
* Created by tanvimehta on 15-01-18.
*/
@NodeEntity
public class ResourceNode {

    @GraphId
    Long id;
    private Long resourceId;
    private String resourceType;
    private String resourceName;

    public ResourceNode() {}

    public ResourceNode(long resourceId, String resourceType, String resourceName) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.resourceName = resourceName;
    }

    @RelatedTo(type="CONNECTS_TO", direction = Direction.BOTH)
    public @Fetch
    Set<ResourceNode> neighbours;

    public void connectsTo(ResourceNode resourceNode) {
        if (neighbours == null) {
            neighbours = new HashSet<ResourceNode>();
        }
        neighbours.add(resourceNode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String toString() {
        String results = resourceName + " has the type " + resourceType +" and its neighbours include\n";
        if (neighbours != null) {
            for (ResourceNode resourceNode : neighbours) {
                results += "\t- " + resourceNode.resourceName + "\n";
            }
        }
        return results;
    }
}
