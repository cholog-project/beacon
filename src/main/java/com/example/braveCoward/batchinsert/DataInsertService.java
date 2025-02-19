package com.example.braveCoward.batchinsert;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataInsertService {
    private final JdbcTemplate jdbcTemplate;
    private final Random random = new Random();

    public void insertLargeData(int numTeams) {
        List<Object[]> teamBatch = new ArrayList<>();
        List<Object[]> userBatch = new ArrayList<>();
        List<Object[]> teamMemberBatch = new ArrayList<>();
        List<Object[]> projectBatch = new ArrayList<>();
        List<Object[]> planBatch = new ArrayList<>();
        List<Object[]> doBatch = new ArrayList<>();

        // 🔹 Step 1: `team` 데이터 삽입
        for (int t = 0; t < numTeams; t++) {
            String teamName = "Team-" + UUID.randomUUID().toString().substring(0, 8);
            String teamDesc = "This is " + teamName;
            LocalDateTime createdAt = randomDateTime();
            LocalDateTime updatedAt = createdAt.plusDays(random.nextInt(30));
            teamBatch.add(new Object[]{teamName, teamDesc, createdAt, updatedAt});
        }
        batchInsert("INSERT INTO team (name, description, created_at, updated_at) VALUES (?, ?, ?, ?)", teamBatch);
        teamBatch.clear();

        // 🔹 Step 2: `team_id` 가져오기
        List<Long> teamIds = getInsertedIds("team", numTeams);

        // 🔹 Step 3: `user` 데이터 삽입
        for (int i = 0; i < numTeams * 6; i++) {
            String userName = "User-" + UUID.randomUUID().toString().substring(0, 6);
            String email = userName.toLowerCase() + "@example.com";
            LocalDateTime createdAt = randomDateTime();
            LocalDateTime updatedAt = createdAt.plusDays(random.nextInt(30));
            userBatch.add(new Object[]{"password123", userName, email, createdAt, updatedAt, false});
        }
        batchInsert("INSERT INTO user (password, name, email, created_at, updated_at, is_deleted) VALUES (?, ?, ?, ?, ?, ?)", userBatch);
        userBatch.clear();

        // 🔹 Step 4: `user_id` 가져오기
        List<Long> userIds = getInsertedIds("user", numTeams * 6);

        // 🔹 Step 5: `team_member` 데이터 삽입
        int userIndex = 0;
        for (Long teamId : teamIds) {
            for (int u = 0; u < 6; u++) {
                LocalDateTime createdAt = randomDateTime();
                LocalDateTime updatedAt = createdAt.plusDays(random.nextInt(30));
                teamMemberBatch.add(new Object[]{teamId, userIds.get(userIndex), "Member", "Developer", createdAt, updatedAt});
                userIndex++;
            }
        }
        batchInsert("INSERT INTO team_member (team_id, user_id, role, position, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)", teamMemberBatch);
        teamMemberBatch.clear();

        // 🔹 Step 6: `project` 데이터 삽입
        List<Long> projectIds = new ArrayList<>();
        for (Long teamId : teamIds) {
            String projectTitle = "Project-" + UUID.randomUUID().toString().substring(0, 5);
            String projectDesc = "Description for " + projectTitle;
            LocalDate startDate = randomDate();
            LocalDate endDate = startDate.plusDays(random.nextInt(30));
            LocalDateTime createdAt = randomDateTime();
            LocalDateTime updatedAt = createdAt.plusDays(random.nextInt(30));

            projectBatch.add(new Object[]{teamId, projectTitle, projectDesc, startDate, endDate, 0.0, createdAt, updatedAt});
        }
        batchInsert("INSERT INTO project (team_id, title, description, start_date, end_date, progress, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", projectBatch);
        projectIds.addAll(getInsertedIds("project", numTeams));
        projectBatch.clear();

        // 팀별 TeamMember 리스트를 저장하는 맵
        Map<Long, List<Long>> teamToTeamMembersMap = new HashMap<>();

        // 🔹 Step 6: `team_member_id`를 팀별로 저장
        for (Long teamId : teamIds) {
            List<Long> membersInTeam = getInsertedIdsByTeam("team_member", teamId);
            teamToTeamMembersMap.put(teamId, membersInTeam);
        }

        // 🔹 Step 7: `plan` 데이터 삽입
        List<Long> planIds = new ArrayList<>();

        for (Long projectId : projectIds) {
            Long teamId = getTeamIdForProject(projectId); // ✅ 프로젝트에 속한 팀 ID 가져오기
            List<Long> teamMemberList = teamToTeamMembersMap.get(teamId); // ✅ 해당 팀의 팀원 리스트

            if (teamMemberList == null || teamMemberList.isEmpty()) {
                continue; // 만약 팀원이 없다면 건너뛰기
            }

            for (int p = 0; p < 80; p++) {
                String planTitle = "Plan-" + UUID.randomUUID().toString().substring(0, 5);
                String planDescription = "Description - " + UUID.randomUUID().toString().substring(0, 10);
                LocalDate startDate = LocalDate.now();
                LocalDate endDate = startDate.plusDays(random.nextInt(30));
                LocalDateTime createdAt = randomDateTime();
                LocalDateTime updatedAt = createdAt.plusDays(random.nextInt(30));

                // 🔹 해당 팀에 속한 멤버 중 랜덤 선택
                Long randomTeamMemberId = teamMemberList.get(random.nextInt(teamMemberList.size()));

                planBatch.add(new Object[]{projectId, randomTeamMemberId, planTitle, planDescription, startDate, endDate, "NOT_STARTED", createdAt, updatedAt});
            }
        }
        batchInsert("INSERT INTO plan (project_id, team_member_id, title, description, start_date, end_date, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", planBatch);
        planIds.addAll(getInsertedIds("plan", numTeams * 80));
        planBatch.clear();

        // 🔹 Step 8: `do` 데이터 삽입
        for (Long planId : planIds) {
            Long projectId = getProjectIdForPlan(planId); // ✅ Plan에 해당하는 Project ID 가져오기

            for (int d = 0; d < 6; d++) {
                LocalDate date = LocalDate.now().minusDays(random.nextInt(30));
                String description = "Do " + UUID.randomUUID().toString().substring(0, 5);
                LocalDateTime createdAt = randomDateTime();
                LocalDateTime updatedAt = createdAt.plusDays(random.nextInt(30));

                doBatch.add(new Object[]{projectId, planId, date, description, false, createdAt, updatedAt});
            }
        }
        batchInsert("INSERT INTO do (project_id, plan_id, date, description, is_completed, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)", doBatch);
        doBatch.clear();

        System.out.println("랜덤 데이터 삽입 완료!");
    }

    private void batchInsert(String sql, List<Object[]> batchList) {
        jdbcTemplate.batchUpdate(sql, batchList);
    }

    private List<Long> getInsertedIds(String tableName, int limit) {
        return jdbcTemplate.queryForList("SELECT id FROM " + tableName + " ORDER BY id DESC LIMIT ?", Long.class, limit);
    }

    private LocalDateTime randomDateTime() {
        return LocalDateTime.now().minusDays(random.nextInt(365));
    }

    private LocalDate randomDate() {
        return LocalDate.now().minusDays(random.nextInt(365));
    }

    // 특정 팀의 team_member_id 리스트 가져오기
    private List<Long> getInsertedIdsByTeam(String tableName, Long teamId) {
        return jdbcTemplate.queryForList("SELECT id FROM " + tableName + " WHERE team_id = ?", Long.class, teamId);
    }

    // 특정 프로젝트가 속한 팀 ID 가져오기
    private Long getTeamIdForProject(Long projectId) {
        return jdbcTemplate.queryForObject("SELECT team_id FROM project WHERE id = ?", Long.class, projectId);
    }

    // 특정 Plan이 속한 Project의 ID 가져오기
    private Long getProjectIdForPlan(Long planId) {
        return jdbcTemplate.queryForObject("SELECT project_id FROM plan WHERE id = ?", Long.class, planId);
    }
}