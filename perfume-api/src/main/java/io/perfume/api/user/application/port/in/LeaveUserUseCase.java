package io.perfume.api.user.application.port.in;

public interface LeaveUserUseCase {

    /**
     * 본인의 계정을 탈퇴처리한다.
     *
     * @param userId 회원 탈퇴를 희망하는 사용자 데이터 식별자
     */
    void leave(Long userId);
}
