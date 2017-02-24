package com.yoflying.drivingschool.domain.dao;

import com.yoflying.drivingschool.domain.CommonMapper;
import com.yoflying.drivingschool.domain.jpa.AppointmentSt;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by liqiang on 16/12/15.
 */
@MapperScan
public interface AppointmentStMapper extends CommonMapper<AppointmentSt> {

    @Insert("INSERT INTO appointment_st (dsId,coachId,testCourse,testAddress,studentsIds,appointmentDate,status,modifyTime)" +
            " VALUES( #{dsId},#{coachId},#{testCourse},#{testAddress},#{studentsIds},#{appointmentDate},#{status},NOW())")
    int insertAppointmentSt(AppointmentSt appointmentSt);

    @Update("UPDATE appointment_st SET status = #{status},studentsIds = #{studentsIds}"+
            ",modifyTime = NOW() " +
            "WHERE dsId = #{dsId} and coachId = #{coachId}")
    int updateAppointmentSt(@Param("status") Integer status, @Param("coachId") Long coachId, @Param("dsId") Long dsId, @Param("studentsIds") String studentsIds);

    @Select("SELECT * FROM appointment_st WHERE dsId = #{dsId} and coachId = #{coachId} ORDER BY appointmentDate DESC")
    List<AppointmentSt> findAppointmentStbyDsIDandCoachId(@Param("dsId") Long dsId, @Param("coachId") Long coachId);

    @Select("SELECT * FROM appointment_st WHERE dsId = #{dsId} and coachId = #{coachId} and  testCourse = #{testCourse} ORDER BY appointmentDate DESC")
    List<AppointmentSt> findAppointmentStbyCoachIdandCoures(@Param("dsId") Long dsId, @Param("coachId") Long coachId, @Param("testCourse") int testCoures);

    @Select("SELECT * FROM appointment_st WHERE dsId = #{dsId} and studentsIds LIKE '%${stId}%' ORDER BY appointmentDate DESC")
    List<AppointmentSt> findAppointmentStbyDsIDandStIdAll(@Param("dsId") Long dsId, @Param("stId") Long stId);

    @Select("SELECT * FROM appointment_st WHERE dsId = #{dsId} ORDER BY appointmentDate DESC")
    List<AppointmentSt> findAppointmentStbysDsIdALL(@Param("dsId") Long dsId);

    @Select("SELECT * FROM appointment_st WHERE dsId = #{dsId} and appointmentDate LIKE concat('%',#{adate},'%') " +
            "ORDER BY appointmentDate DESC")
    List<AppointmentSt> findAppointmentStbysDsIdToday(@Param("dsId") Long dsId, @Param("adate") String adate);

    @Delete("DELETE FROM appointment_st WHERE id = #{id}")
    int deleteAppointmentStbyStatusALL(@Param("id") int id);
}
