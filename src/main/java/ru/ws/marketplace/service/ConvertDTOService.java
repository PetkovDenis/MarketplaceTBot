package ru.ws.marketplace.service;

import org.springframework.stereotype.Service;
import ru.ws.marketplace.dto.DTOTChannel;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.repository.ChannelRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertDTOService {

    final ChannelRepository channelRepository;

    public ConvertDTOService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public List<DTOTChannel> getAllDTO() {
        return channelRepository.findAll()
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    private DTOTChannel convertDTO(TChannel channel) {
        DTOTChannel dtoChannel = new DTOTChannel();
        dtoChannel.setId(channel.getId());
        dtoChannel.setName(channel.getName());
        dtoChannel.setDescription(channel.getDescription());
        dtoChannel.setLink(channel.getLink());
        dtoChannel.setPrice(channel.getPrice());
        return dtoChannel;
    }
}
