package com.ticketApp.ticketApp.service.adapter;

import com.ticketApp.ticketApp.dto.*;
import com.ticketApp.ticketApp.entity.CategoryEntity;
import com.ticketApp.ticketApp.entity.EventEntity;
import com.ticketApp.ticketApp.entity.TicketEntity;
import com.ticketApp.ticketApp.entity.UserEntity;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Adapter {
    public static TicketDTO convertTicketEntityToDTO(TicketEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        TicketDTO ticketDTO = modelMapper.map(entity, TicketDTO.class);

        return ticketDTO;
    }

    public static TicketEntity convertTicketDTOToEntity(TicketDTO ticketDTO) {
        ModelMapper modelMapper = new ModelMapper();
        TicketEntity ticket = modelMapper.map(ticketDTO, TicketEntity.class);

        return ticket;
    }

    public static List<TicketDTO> convertTicketEntitiesToDTOs(List<TicketEntity> ticketEntities) {
        return ticketEntities.stream().map(Adapter::convertTicketEntityToDTO).collect(Collectors.toList());
    }

    public static EventDTO convertEventEntityToDTO(EventEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        EventDTO eventDTO = modelMapper.map(entity, EventDTO.class);

        return eventDTO;
    }

    public static EventEntity convertEventDTOToEntity(EventDTO eventDTO) {
        ModelMapper modelMapper = new ModelMapper();
        EventEntity event = modelMapper.map(eventDTO, EventEntity.class);

        return event;
    }

    public static List<EventDTO> convertEventEntitiesToDTOs(List<EventEntity> eventEntities) {
        return eventEntities.stream().map(Adapter::convertEventEntityToDTO).collect(Collectors.toList());
    }

    public static UserDTO convertUserEntityToDTO(UserEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(entity, UserDTO.class);

        return userDTO;
    }

    public static UserEntity convertUserSaveDTOToEntity(UserForSaveDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity entity = modelMapper.map(dto, UserEntity.class);

        return entity;
    }

    public static EventEntity convertEventForSaveDTOToEntity(EventForSaveDTO eventForSaveDTO) {
        ModelMapper modelMapper = new ModelMapper();
        EventEntity event = modelMapper.map(eventForSaveDTO, EventEntity.class);
        return event;
    }

    public static EventForSaveDTO convertEventEntityToEventForSaveDTO(EventEntity eventEntity){
        ModelMapper modelMapper = new ModelMapper();
        EventForSaveDTO eventDto = modelMapper.map(eventEntity, EventForSaveDTO.class);

        return eventDto;
    }

    public static CategoryDTO convertCategoryEntityToDTO(CategoryEntity categoryEntity){
        ModelMapper modelMapper = new ModelMapper();
        CategoryDTO categoryDTO = modelMapper.map(categoryEntity, CategoryDTO.class);

        return categoryDTO;
    }
}
