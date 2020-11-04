package com.ehealth;

import cn.hutool.core.util.ArrayUtil;
import com.ehealth.generator.CodeGenerator;

import java.util.Arrays;

/**
 * 生成代码
 *
 * @author hutu
 * @date 2020/6/5 1:56 下午
 */
public class RunGenerator {
    /**
     * 需要生成的表名数组
     */
    /**
     * eh_base_devtest
     */
    final static String[] tables = {
            "bus_order",
            "sns_weixinmp",
            "wx_config",
            //
            "base_diseas",
            "mpi_healthlog",
            "wx_wxappprops"
    };
    /**
     * eh_basic_devtest
     */
    final static String[] tables1 = {
            "base_department",
            "base_doctor",
            "mpi_patient",
            "base_employment",
            "base_organ",
            //
            "bus_appointdepart",
            "base_user",
            "base_userroles"
    };


    /**
     * eh_follow_devtest
     */
    final static String[] tables2 = {
            "mpi_followplan",
            "mpi_followschedule"
    };

    /**
     * eh_appoint_devtest
     */
    final static String[] tables3 = {
            "bus_appointrecord",
    };

    /**
     * eh_meetclinic_devtest
     */
    final static String[] tables4 = {
            "bus_cloudappointrecord",
            "bus_meetclinic",
            "bus_meetclinicresult"
    };

    /**
     * eh_consult_devtest
     */
    final static String[] tables5 = {
            "bus_consult_msg",
            "bus_consult",
            //
            "bus_consult_ex",
            //
            "bus_consult_questionnaire",

            "bus_hosrecord",
            "bus_hosrecord_detail"
    };
    /**
     * eh_reportpayment_devtest
     */
    final static String[] tables6 = {
            "bus_outpatient",
            "bus_hospitaldata",
            "bus_outpatient"
    };
    /**
     * eh_transfer_devtest
     */
    final static String[] tables7 = {
            "bus_transfer"
    };
    /**
     * eh_recipe_devtest
     */
    final static String[] tables8 = {
            "cdr_recipecheck",
            "cdr_recipeorder",
            "cdr_recipe",
            //
            "cdr_recipe_ext",
            //
            "recipe_symptom",
            "cdr_recipedetail",
            "base_organdruglist"
    };
    /**
     * eh_evaluation_devtest
     */
    final static String[] tables9 = {
            "evaluation_master",
            "evaluation_score"
    };
    /**
     * eh_live_course_devtest
     */
    final static String[] tables10 = {
            "live_course",
            "live_course_member"
    };
    /**
     * eh_bodycheck_devtest
     */
    final static String[] tables11 = {
            "bus_bodycheck"
    };
    /**
     * eh_cdr_devtest
     */
    final static String[] tables12 = {
            "bus_checkrequest",
            "bus_labrecord",
            //
            "cdr_medicaldetail",
            //
            "cdr_docindex_ext",
            "cdr_docindex"
    };

    public static void main(String[] args) {
//        String[] allTables = ArrayUtil.addAll(tables, tables1, tables5, tables8, tables12);
        String[] allTables = ArrayUtil.addAll(
                tables,
                tables1,
                tables2,
                tables3,
                tables4,
                tables5,
                tables6,
                tables7,
                tables8,
                tables9,
                tables10,
                tables11,
                tables12);

        new CodeGenerator()
                .setIncludeTables(new String[]{"base_department"})
                .setTablePrefix(new String[]{
                        "bus_"
                        ,"sns_"
                        ,"wx_"
                        ,"base_"
                        ,"mpi_"
                        ,"cdr_"
                        ,"evaluation_"
                        ,"live_"
                })
                .setServiceName("ehealth-snyc-localhospital")
                .setPackageName("com.ehealth")
                .setPackageDir("./genCode")
                .setIsSwagger2(Boolean.FALSE)
                .run();
    }

}
