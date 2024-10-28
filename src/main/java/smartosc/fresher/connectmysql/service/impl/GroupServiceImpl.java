package smartosc.fresher.connectmysql.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartosc.fresher.connectmysql.model.Group;
import smartosc.fresher.connectmysql.repository.GroupRepository;
import smartosc.fresher.connectmysql.service.GroupService;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }
}
