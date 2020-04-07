package zwm.forum.community.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import zwm.forum.community.community.model.User;

@Component
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,accountId,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);

    @Select("select * from user where token = #{token}")
    User finToken(@Param("token") String token);
}
