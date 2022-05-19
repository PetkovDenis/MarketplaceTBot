package ru.ws.marketplace.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ws.marketplace.dto.TChannelDTO;
import ru.ws.marketplace.model.TChannel;

@Mapper
public interface ChannelMapper {

    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    //    @Mapping(source = "id", target = "id")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "category", target = "category")
//    @Mapping(source = "description", target = "description")
//    @Mapping(source = "link", target = "link")
//    @Mapping(source = "price", target = "price")
    TChannelDTO channelToDTOChannel(TChannel channel);

}
