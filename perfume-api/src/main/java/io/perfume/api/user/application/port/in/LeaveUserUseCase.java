package io.perfume.api.user.application.port.in;

public interface LeaveUserUseCase {

    /**
     * 본인의 계정을 탈퇴처리한다.
     *
     * @param accessToken 삭제를 위한 User 데이터 조회에 필요한 값이 담겨있다.
     */
    void leave(String accessToken);
}
