
input1 = [(1,3,5,'yellow'), (2,5,8,'green'), (4,7,9,'yellow'), (6,8,2,'green'), (9,11,3,'yellow'), (10,12,1,'yellow')]

def YELLOW_GREEN_INC(intervals):
    N = len(intervals)
    memo = [0] * N
    for j in range(N):
        (sj, fj, cj, dj) = intervals[j] 
        memo[j] = cj
        currentF = fj
        colorNow = dj
        for i in range(j + 1, N):
            (si, fi, ci, di) = intervals[i]
            if currentF <= si and colorNow != di:
                memo[j] += ci
                colorNow = di
                currentF = fi
    return max(memo)

def YELLOW_GREEN_DEC(intervals):
    N = len(intervals)
    memo = [0] * N
    for j in range(N):
        (sj, fj, cj, dj) = intervals[j]
        memo[j] = cj
        for i in range(j - 1, -1, -1):
            (si, fi, ci, di) = intervals[i]
            if dj != di and fi <= sj and memo[j] < memo[i] + cj:
                memo[j] = memo[i] + cj 
    print(memo)
    return max(memo)

input2 = [(1, 4), (3, 5), (0, 6), (4, 7), (3, 8), (5, 9), (6, 10), (8, 11)]

def binarySearchForLatestFinishingInterval(left, right, intervals, j):
    if left == right and intervals[left][1] <= intervals[j][0]:
        return left
    if left >= right:
        return -1 
    mid = (left + right) // 2
    (smid, fmid) = intervals[mid] 
    (sfind, ffind) = intervals[j]
    if fmid <= sfind:
        return max(binarySearchForLatestFinishingInterval(mid + 1, right, intervals, j), mid)
    else:
        return binarySearchForLatestFinishingInterval(left, mid - 1, intervals, j)
    
p = [binarySearchForLatestFinishingInterval(0, j - 1, input2, j) + 1 for j in range(len(input2))]

numbers = [2, 4, 7, 1, 5]

def longestIncreasingSubSequence(nums):
    N = len(nums)
    memo = [1] * N
    for i in range(N):
        for j in range(i):
            if nums[j] < nums[i] and memo[i] <= memo[j]:
                memo[i] = memo[j] + 1
    
    # Finds the actual experience in O(n^2)
    maxMemo = -1
    maxIdx = -1
    for i in range(N):
        if maxMemo < memo[i]:
            maxMemo = memo[i]
            maxIdx = i
    seq = []
    seq.append(nums[maxIdx])

    for i in range(maxMemo - 1, -1, -1):
        for j in range(maxIdx):
            if memo[j] == i:
                seq.append(nums[j])
                maxIdx = i
                break
    seq.reverse()
    print(seq)
    return max(memo)

def countIncreasingSubsequences( nums ):
    N = len( nums )
    memo = [0] * ( N + 1 )

    for i in range( 1, N + 1 ):
        memo[i] = 1
        for j in range( i ):
            if nums[j - 1] < nums[i - 1]:
                memo[i] += memo[j]
    return sum( memo )

def countIncreasingSubsequencesDecrementing( nums ):
    N = len( nums )
    memo = [0] * ( N + 1 )

    for i in range( N - 1, -1, -1 ):
        memo[i] = 1
        for j in range( i + 1, N ):
            if nums[i] < nums[j]:
                memo[i] += memo[j]

    return sum( memo )

def lengthLongestCommonIncreasingSubsequence( s1, s2 ):
    M, N = len( s1 ), len( s2 )
    memo = [[0] * ( N + 1 ) for _ in range( M + 1 )]
    for i in range(M):
        for j in range(N):
            memo[i + 1][j + 1] = max(memo[i + 1][j], memo[i][j + 1])
            if s1[i] == s2[j]:
                memo[i + 1][j + 1] = 1 + memo[i][j]

    lcs, i, j = [], M, N
    while memo[i][j]:
            if s1[i - 1] == s2[j - 1]:
                lcs.append(s1[i - 1])
                i -= 1
                j -= 1
            else:
                if memo[i - 1][j] > memo[i][j - 1]:
                    i -= 1
                else:
                    j -= 1

    lcs.reverse()
    print(lcs)
    return memo[M][N]


def findNumberOfCompatibleSubsets(intervals):
    intervals.sort( key = lambda x : x[1] )
    N = len( intervals )
    memo = [0] * ( N + 1 )
    memo[0] = 1
    p = [binarySearchForLatestFinishingInterval(0, j - 1, intervals, j) + 1 for j in range(len(intervals))]
    for i in range( 1, N + 1):
        memo[i] = memo[i - 1] + memo[p[i - 1]]
    print(memo)
    return memo[N]

#print(findNumberOfCompatibleSubsets( [(8, 10), (3, 6), (5, 7), (1, 9), (2, 4)]) )

def weightedIntervalSchedulingProblem(intervals):
    intervals.sort()
    def find(j, left, right):
        if left == right and intervals[j][1] <= intervals[left][0]:
            return left
        if left >= right:
            return len( intervals ) + 1
        mid = (left + right) // 2
        (smid, fmid, _) = intervals[mid]
        (sj, fj, _) = intervals[j]

        if fj <= smid:
            return min(mid, find(j, left, mid - 1))
        else:
            return find(j, mid + 1, right)
    p = [find(i, i + 1, len( intervals ) - 1) for i in range( len( intervals ) )]

    memo = [0] * ( len( intervals ) + 1 )
    memo[len( intervals )] = 0
    for i in range( len(intervals) - 1, -1, -1 ):
        (x, y, w) = intervals[i]
        prev = 0 if p[i] == len(intervals) + 1 else memo[p[i]] 
        memo[i] = max(memo[i + 1], w + prev)

    return memo[0]

print(weightedIntervalSchedulingProblem( [(1,4,1), (3,5,2), (0, 6, 3), (4,7,4), (3,8,5), (5,9,6), (6,10,7), (8,11,8)] ) )