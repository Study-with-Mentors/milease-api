package vn.id.milease.mileaseapi.util.mapper;

public interface Mapper<Entity, Dto, CreateDto, UpdateDto> {
    Entity toEntity(CreateDto dto);
    void toEntity(UpdateDto dto, Entity entity);
    Dto toDto(Entity entity);
}
