package hr.tvz.roommatematcher.mapper;
import hr.tvz.roommatematcher.dto.user.PublicUserResponse;
import hr.tvz.roommatematcher.dto.user.AppUserResponse;
import hr.tvz.roommatematcher.dto.user.UpdateUserRequest;
import hr.tvz.roommatematcher.model.AppUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserResponse toAppUserResponse(AppUser appUser);

    PublicUserResponse toDto(AppUser appUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "listings", ignore = true)
    void updateAppUserFromRequest(UpdateUserRequest request, @MappingTarget AppUser appUser);




}
