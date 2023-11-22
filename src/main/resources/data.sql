-- 일기 가이드 초기 데이터
insert into guide (id, content, description, created_at, last_modified_at, deleted)
values (1,
        "산책에 관한 일상",
        "산책할 때 어떤 일이 있었나요?",
        now(), now(),
        0);

insert into guide (id, content, description, created_at, last_modified_at, deleted)
values (2,
        "낮잠에 관한 일상",
        "낮잠을 잘 때 어떤 일이 있었나요?",
        now(), now(),
        0);

insert into guide (id, content, description, created_at, last_modified_at, deleted)
values (3,
        "놀이에 관한 일상",
        "놀 때 어떤 일이 있었나요?",
        now(), now(),
        0);

insert into guide (id, content, description, created_at, last_modified_at, deleted)
values (4,
        "간식에 관한 일상",
        "간식을 먹을 때 어떤 일이 있었나요?",
        now(), now(),
        0);

insert into guide (id, content, description, created_at, last_modified_at, deleted)
values (5,
        "표현(기분)에 관한 일상",
        "오늘은 어떤 것을 느꼈나요?",
        now(), now(),
        0);


-- 일기 가이드 디테일 초기 데이터
-- 가이드 1: 산책에 관한 일상
insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (1,
        1,
        "산책을 오래 했어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (2,
        1,
        "산책 중에 새로운 친구를 만났어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (3,
        1,
        "햇볕이 따뜻해서 기분 좋았어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (4,
        1,
        "풀밭에서 놀며 즐거운 시간을 보냈어요",
        now(), now(),
        0);

-- 가이드 2: 낮잠에 관한 일상
insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (5,
        2,
        "낮잠을 자면서 꿈을 꿨어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (6,
        2,
        "부드러운 이불에서 푹 자서 기분이 좋았어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (7,
        2,
        "친구들과 함께 낮잠 자는 시간이었어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (8,
        2,
        "낮잠 후에는 더 활기찬 강아지가 되었어요",
        now(), now(),
        0);

-- 가이드 3: 놀이에 관한 일상
insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (9,
        3,
        "공놀이를 하면서 신나게 뛰었어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (10,
        3,
        "물장구를 갖고 놀면서 즐거운 시간을 보냈어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (11,
        3,
        "친구들과 함께 꼬리를 흔들며 놀았어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (12,
        3,
        "잡기놀이를 하면서 놀이의 실력을 향상시켰어요",
        now(), now(),
        0);

-- 가이드 4: 간식에 관한 일상
insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (13,
        4,
        "맛있는 간식을 받아서 즉시 먹어버렸어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (14,
        4,
        "간식 시간이 가장 기다려지는 순간이에요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (15,
        4,
        "간식을 받으려고 꼬리를 살랑거렸어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (16,
        4,
        "간식을 먹은 후에는 행복한 강아지로 변신했어요",
        now(), now(),
        0);

-- 가이드 5: 표현(기분)에 관한 일상
insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (17,
        5,
        "오늘은 기분이 좋아서 꼬리를 더 많이 흔들었어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (18,
        5,
        "조금 피곤해서 눈을 반쯤 감았어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (19,
        5,
        "친구들과 놀면서 행복한 기분을 느꼈어요",
        now(), now(),
        0);

insert into guide_detail (id, guide_id, content, created_at, last_modified_at, deleted)
values (20,
        5,
        "비 오는 날이라 조금 우울했어요",
        now(), now(),
        0);
