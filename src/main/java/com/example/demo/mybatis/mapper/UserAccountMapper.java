package com.example.demo.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import com.example.demo.mybatis.model.UserAccountEntity;
import com.example.demo.mybatis.model.UserAccount;
import com.example.demo.mybatis.model.UserAccountExample;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAccountMapper {
    
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	long countByExample(UserAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	int deleteByExample(UserAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	int insert(UserAccount row);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	int insertSelective(UserAccount row);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	List<UserAccount> selectByExample(UserAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	UserAccount selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	int updateByExampleSelective(@Param("row") UserAccount row, @Param("example") UserAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	int updateByExample(@Param("row") UserAccount row, @Param("example") UserAccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	int updateByPrimaryKeySelective(UserAccount row);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hpm_user_account
	 * @mbg.generated  Tue Apr 02 11:27:06 ICT 2024
	 */
	int updateByPrimaryKey(UserAccount row);

	// @Select("select * from hpm_user_account")
    public List<UserAccountEntity> findAll();

    @Select("select * from hpm_user_account")
    public List<UserAccountEntity> all();
}
