INSERT INTO users (id, age, gender, name, classification_name, location_sido_name, monthly_sales)
VALUES (1234, 20, 'MALE', '정용희', '의료 서비스업', '서울특별시', 1000000);

INSERT INTO campaigns (id, placement)
VALUES (1, 'banking_top');

INSERT INTO ad_groups (id, enabled, start_time, end_time, priority, classification_target_include, classification_targets,
                       location_sido_target_include, location_sido_targets, monthly_sales_comparison, monthly_sales_targets, campaign_id)
VALUES (1, TRUE, '2024-01-01 00:00:00', '2024-12-31 23:59:59', 3.0, 'INCLUDE', '["의료 서비스업"]'::jsonb, 'INCLUDE', '["서울특별시"]'::jsonb, 'GT', 100000, 1),
       (2, TRUE, '2024-01-01 00:00:00', '2024-12-31 23:59:59', 1.0, NULL, NULL, NULL, NULL, NULL, NULL, 1);

INSERT INTO creatives(id, title, description, text_color, background_color, background_image, url, ad_group_id)
VALUES (2, '캐시노트 대출 상품 확인', '비대면으로 신청부터 상담 연결', '#292929', '#D9E8FF',
        'https://bluebird-asset.cashnote.kr/uploads/image/image/36714/resized_IBKBOX_360x360_20240227.png',
        'https://finance-bridge.cashnote.kr/?companyLink=https%3A%2F%2F365.ibkbox.net%3Futm_source%3Dcashnote%26utm_medium%3Daffiliate%26utm_campaign%3DLOAN_2402_MO%26utm_content%3D1&companyName=IBK%EA%B8%B0%EC%97%85%EC%9D%80%ED%96%89',
        1),
       (5, '이 혜택 놓쳐도 괜찮으신가요?', 'IBK 대출 상품 확인', '#292929', '#000000',
        'https://bluebird-asset.cashnote.kr/uploads/image/image/36714/resized_IBKBOX_360x360_20240227.png',
        'https://finance-bridge.cashnote.kr/?companyLink=https%3A%2F%2F365.ibkbox.net%3Futm_source%3Dcashnote%26utm_medium%3Daffiliate%26utm_campaign%3DLOAN_2402_MO%26utm_content%3D1&companyName=IBK%EA%B8%B0%EC%97%85%EC%9D%80%ED%96%89',
        2),
       (7, '이 혜택 놓쳐도 괜찮으신가요(2)?', 'KB캐피탈 대출 상품 확인 (2)', '#292929', '#000000',
        'https://bluebird-asset.cashnote.kr/uploads/image/image/36714/resized_IBKBOX_360x360_20240227.png',
        'https://finance-bridge.cashnote.kr/?companyLink=https%3A%2F%2F365.ibkbox.net%3Futm_source%3Dcashnote%26utm_medium%3Daffiliate%26utm_campaign%3DLOAN_2402_MO%26utm_content%3D1&companyName=IBK%EA%B8%B0%EC%97%85%EC%9D%80%ED%96%89',
        2);

