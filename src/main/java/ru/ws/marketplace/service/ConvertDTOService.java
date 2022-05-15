package ru.ws.marketplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.dto.TChannelDTO;
import ru.ws.marketplace.mapper.ChannelMapper;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.repository.ChannelRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConvertDTOService {

    private final ChannelRepository channelRepository;

    public List<TChannelDTO> getAllDTO() {
        return channelRepository.findAll()
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    private TChannelDTO convertDTO(TChannel channel) {
        return ChannelMapper.INSTANCE.channelToDTOChannel(channel);
    }
}
